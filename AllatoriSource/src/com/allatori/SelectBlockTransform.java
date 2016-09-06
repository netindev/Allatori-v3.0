package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Select;

public class SelectBlockTransform implements ControlFlowTransform {

	/* OK */
	
	@Override
	public void patch(ClassGen classGen) {
		final Method[] methodArray = classGen.getMethods();
		for (int i = 0; i < methodArray.length; i++) {
			Method actualMethod = methodArray[i];
			if (actualMethod.getCode() != null) {
				MethodGen methodGen = InitUtils.createMethodGen(actualMethod, classGen.getClassName(), classGen.getConstantPool(),
						classGen.getConstantPool().getConstantPool());
				InstructionList instructionList = methodGen.getInstructionList();
				instructionList.setPositions();
				for (InstructionHandle instructionHandle = instructionList.getStart(); instructionHandle != null; instructionHandle.getNext()) {
					if (instructionHandle.getInstruction() instanceof Select) {
						final Select select = (Select) instructionHandle.getInstruction();
						final InstructionHandle[] targets = select.getTargets();
						for (int j = targets.length - 1; j >= 0; j--) {
							final InstructionHandle currentTarget = targets[j];
							if (!(currentTarget.getInstruction() instanceof BranchInstruction)
									&& currentTarget != select.getTarget()) {
								final InstructionHandle instructionHandleAP = instructionList.append(currentTarget, new ICONST(0));
								instructionList.append(instructionHandleAP, new IFNE(instructionHandleAP));
								break;
							}
						}
					}
				}
				methodGen.setMaxStack();
				classGen.replaceMethod(actualMethod, methodGen.getMethod());
			}
		}
	}
}
