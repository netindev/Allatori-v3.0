package com.allatori.obfuscate.opt;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ANEWARRAY;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.GotoInstruction;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.LineNumberGen;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.NEWARRAY;

import com.allatori.ControlFlowTransform;
import com.allatori.InitUtils;

public class BranchTransform implements ControlFlowTransform {

	@Override
	public void patch(ClassGen _cg) {
		for (final Method method : _cg.getMethods()) {
			if (method.getCode() != null) {
				final MethodGen _mg = InitUtils.createMethodGen(method, _cg.getClassName(), _cg.getConstantPool(),
						_cg.getConstantPool().getConstantPool());
				final InstructionList _il = _mg.getInstructionList();
				_il.setPositions();
				for (InstructionHandle current = _il.getStart(); current != null; current = current.getNext()) {
					if (current.getInstruction() instanceof IfInstruction) {
						boolean targetPosLower = true;
						boolean targetPosHigher = true;
						final InstructionHandle target = ((IfInstruction) current.getInstruction()).getTarget();
						if (target.getPosition() >= current.getPosition()) {
							targetPosLower = false;
						}

						if (target.getPosition() <= current.getPosition()) {
							targetPosHigher = false;
						}

						InstructionHandle targetPrev;
						if ((targetPosLower || targetPosHigher) && (targetPrev = target.getPrev()) != null
								&& targetPrev.getInstruction() instanceof GotoInstruction) {
							final GotoInstruction gi = (GotoInstruction) targetPrev.getInstruction();
							final InstructionHandle targetPrevTarget = gi.getTarget();

							if ((targetPosLower && (targetPrevTarget.getPosition() >= current.getPosition()
									|| targetPrevTarget.getPosition() >= targetPrevTarget.getPosition()))
									|| targetPrevTarget.getPosition() >= current.getPosition()) {
								continue;
							}

							if (!(targetPrevTarget.getInstruction() instanceof BranchInstruction)
									&& !(targetPrevTarget.getInstruction() instanceof NEW)
									&& !(targetPrevTarget.getInstruction() instanceof NEWARRAY)
									&& !(targetPrevTarget.getInstruction() instanceof ANEWARRAY)
									&& this.hasOnlyNonInstructionTargeters(targetPrevTarget.getPrev().getTargeters())) {
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
		for (final InstructionTargeter targeter : var1) {
			if (!(targeter instanceof LineNumberGen) && !(targeter instanceof LocalVariableGen)) {
				return false;
			}
		}

		return true;

	}
}
