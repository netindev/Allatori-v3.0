package com.allatori.obfuscate.opt;

import com.allatori.ControlFlowTransform;
import com.allatori.InitUtils;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

public class BranchTransform implements ControlFlowTransform {

    public void patch(ClassGen _cg) {
        for (Method method : _cg.getMethods()) {
            if (method.getCode() != null) {
                MethodGen _mg = InitUtils.createMethodGen(method, _cg.getClassName(), _cg.getConstantPool(), _cg.getConstantPool().getConstantPool());
                InstructionList _il = _mg.getInstructionList();
                _il.setPositions();
                for (InstructionHandle current = _il.getStart(); current != null; current = current.getNext()) {
                    if (current.getInstruction() instanceof IfInstruction) {
                        boolean targetPosLower = true;
                        boolean targetPosHigher = true;
                        InstructionHandle target = ((IfInstruction) current.getInstruction()).getTarget();
                        if (target.getPosition() >= current.getPosition()) {
                            targetPosLower = false;
                        }

                        if (target.getPosition() <= current.getPosition()) {
                            targetPosHigher = false;
                        }

                        InstructionHandle targetPrev;
                        if ((targetPosLower || targetPosHigher) && (targetPrev = target.getPrev()) != null && targetPrev.getInstruction() instanceof GotoInstruction) {
                            GotoInstruction gi = (GotoInstruction) targetPrev.getInstruction();
                            InstructionHandle targetPrevTarget = gi.getTarget();

                            if ((targetPosLower && (targetPrevTarget.getPosition() >= current.getPosition() || targetPrevTarget.getPosition() >= targetPrevTarget.getPosition()))|| targetPrevTarget.getPosition() >= current.getPosition()) {
                                continue;
                            }

                            if (!(targetPrevTarget.getInstruction() instanceof BranchInstruction) && !(targetPrevTarget.getInstruction() instanceof NEW) && !(targetPrevTarget.getInstruction() instanceof NEWARRAY) && !(targetPrevTarget.getInstruction() instanceof ANEWARRAY) && this.hasOnlyNonInstructionTargeters(targetPrevTarget.getPrev().getTargeters())) {
                                gi.setTarget(targetPrevTarget.getNext());
                                _il.insert(targetPrevTarget.getPrev(), targetPrevTarget.getInstruction());
                            }
                        }
                    }
                }

                _mg.setMaxStack();
                _cg.replaceMethod(method, _mg.getMethod());
            }
        }

    }

    private boolean hasOnlyNonInstructionTargeters(InstructionTargeter[] var1) {
        if (var1 == null) {
            return true;
        }
        for (InstructionTargeter targeter : var1) {
            if (!(targeter instanceof LineNumberGen) && !(targeter instanceof LocalVariableGen)) {
                return false;
            }
        }

        return true;

    }
}
