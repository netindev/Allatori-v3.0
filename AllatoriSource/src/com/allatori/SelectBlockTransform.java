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
		final Method[] methods = classGen.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getCode() != null) {
				MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(), classGen.getConstantPool(),
						classGen.getConstantPool().getConstantPool());
				InstructionList instructionList = methodGen.getInstructionList();
				instructionList.setPositions();
				InstructionHandle currentHandle;
				for (InstructionHandle tempHandle = currentHandle = instructionList.getStart(); tempHandle != null; tempHandle = currentHandle) {
					if (currentHandle.getInstruction() instanceof Select) {
						final Select select = (Select) currentHandle.getInstruction();
						final InstructionHandle[] targets = select.getTargets();
						for (int j = targets.length - 1; j >= 0; j--) {
							final InstructionHandle currentTarget = targets[j];
							if (!(currentTarget.getInstruction() instanceof BranchInstruction)
									&& currentTarget != select.getTarget()) {
								final InstructionHandle instructionHandleA = instructionList.append(currentTarget, new ICONST(0));
								instructionList.append(instructionHandleA, new IFNE(instructionHandleA));
								break;
							}
						}
					}
					currentHandle = currentHandle.getNext();
				}
				methodGen.setMaxStack();
				classGen.replaceMethod(method, methodGen.getMethod());
			}
		}
	}
}
