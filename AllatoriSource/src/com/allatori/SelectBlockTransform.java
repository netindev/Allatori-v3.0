package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

public class SelectBlockTransform implements ControlFlowTransform {

    public void patch(ClassGen var1) {
        Method[] var2 = var1.getMethods();

        int index;
        for (int var10000 = index = 0; var10000 < var2.length; var10000 = index) {
            Method var4;
            if ((var4 = var2[index]).getCode() != null) {
                MethodGen var5;
                InstructionList var6;
                (var6 = (var5 = InitUtils.createMethodGen(var4, var1.getClassName(), var1.getConstantPool(), var1.getConstantPool().getConstantPool())).getInstructionList()).setPositions();

                InstructionHandle currentHandle;
                for (InstructionHandle var14 = currentHandle = var6.getStart(); var14 != null; var14 = currentHandle) {
                    if (currentHandle.getInstruction() instanceof Select) {
                        Select select = (Select) currentHandle.getInstruction();
                        InstructionHandle[] targets = select.getTargets();
                        int pos = targets.length - 1;

                        for (var10000 = pos; var10000 >= 0; var10000 = pos) {
                            InstructionHandle currentTarget = targets[pos];
                            if (!(currentTarget.getInstruction() instanceof BranchInstruction) && currentTarget != select.getTarget()) {
                                InstructionHandle var13 = var6.append(currentTarget, new ICONST(0));
                                var6.append(var13, new IFNE(var13));
                                break;
                            }

                            --pos;
                        }
                    }

                    currentHandle = currentHandle.getNext();
                }

                var5.setMaxStack();
                var1.replaceMethod(var4, var5.getMethod());
            }

            ++index;
        }

    }
}
