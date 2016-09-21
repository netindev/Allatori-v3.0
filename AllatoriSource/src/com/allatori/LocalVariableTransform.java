package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.DSTORE;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.DUP2;
import org.apache.bcel.generic.FSTORE;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.LSTORE;
import org.apache.bcel.generic.LineNumberGen;
import org.apache.bcel.generic.LoadInstruction;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.LocalVariableInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.StoreInstruction;
import org.apache.bcel.generic.TargetLostException;

public class LocalVariableTransform implements ControlFlowTransform {
	
	/* OK */

	@Override
	public void patch(ClassGen classGen) {
		final Method[] methods = classGen.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getCode() != null) {
				MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(),
						classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
				InstructionList instructionList = methodGen.getInstructionList();
				instructionList.setPositions();
				InstructionHandle current;
				for (InstructionHandle instructionHandle = current = instructionList
						.getStart(); instructionHandle != null; instructionHandle = current = current.getNext()) {
					if (current.getInstruction() instanceof StoreInstruction && current.getNext() != null
							&& current.getNext().getInstruction() instanceof LoadInstruction) {
						final InstructionHandle next = current.getNext();
						final StoreInstruction storeInstruction = (StoreInstruction) current.getInstruction();
						final LoadInstruction loadInstruction = (LoadInstruction) next.getInstruction();
						if (storeInstruction.getIndex() == loadInstruction.getIndex()
								&& this.notInstanceofLNGOrLVG(next.getTargeters())
								&& this.notInstanceofLNGOrLVG(current.getTargeters())) {
							InstructionHandle nextNext;
							if (this.localVariableWithSameValueNOTExist(instructionList, current, next)) {
								if ((nextNext = next.getNext()) != null) {
									this.updateTargeters(current.getTargeters(), current, nextNext);
									this.updateTargeters(next.getTargeters(), next, nextNext);
									try {
										instructionList.delete(storeInstruction);
									} catch (final TargetLostException e) {
										this.updateTargeters(e, nextNext);
									}
									try {
										instructionList.delete(loadInstruction);
									} catch (final TargetLostException e) {
										this.updateTargeters(e, nextNext);
									}
								}
							} else {
								if (!(storeInstruction instanceof ASTORE) && !(storeInstruction instanceof FSTORE)
										&& !(storeInstruction instanceof ISTORE)) {
									if (storeInstruction instanceof DSTORE || storeInstruction instanceof LSTORE) {
										instructionList.insert(current, new DUP2());
									}
								} else {
									instructionList.insert(current, new DUP());
								}
								if ((nextNext = next.getNext()) != null) {
									this.updateTargeters(next.getTargeters(), next, nextNext);
									try {
										instructionList.delete(next);
									} catch (final TargetLostException e) {
										this.updateTargeters(e, nextNext);
									}
								}
							}
						}
					}
				}
				methodGen.setMaxStack();
				classGen.replaceMethod(method, methodGen.getMethod());
			}
		}
	}

	private void updateTargeters(TargetLostException targetLostException, InstructionHandle instructionHandle) {
		final InstructionHandle[] instructionHandleArr = targetLostException.getTargets();
		for (int i = 0; i < instructionHandleArr.length; i++) {
			final InstructionTargeter[] instructionTargeterArr = instructionHandleArr[i].getTargeters();
			for (int j = 0; j < instructionTargeterArr.length; j++) {
				instructionTargeterArr[j].updateTarget(instructionHandleArr[i], instructionHandle);
				++j;
			}
		}
	}

	private boolean notInstanceofLNGOrLVG(InstructionTargeter[] instructionTargeterArr) {
		if (instructionTargeterArr == null) {
			return true;
		} else {
			for (int i = 0; i < instructionTargeterArr.length; i++) {
				InstructionTargeter instructionTargeter;
				if (!((instructionTargeter = instructionTargeterArr[i]) instanceof LineNumberGen)
						&& !(instructionTargeter instanceof LocalVariableGen)) {
					return false;
				}
			}
			return true;
		}
	}

	private boolean localVariableWithSameValueNOTExist(InstructionList instructionList,
			InstructionHandle instructionHandle, InstructionHandle toCompare) {
		final int index = ((LocalVariableInstruction) instructionHandle.getInstruction()).getIndex();
		InstructionHandle actual;
		for (InstructionHandle instructionHandleIt = actual = instructionList
				.getStart(); instructionHandleIt != null; instructionHandleIt = actual = actual.getNext()) {
			Instruction ins = actual.getInstruction();
			if (ins instanceof LocalVariableInstruction && ((LocalVariableInstruction) ins).getIndex() == index
					&& actual != instructionHandle && actual != toCompare) {
				return false;
			}
		}
		return true;
	}

	private void updateTargeters(InstructionTargeter[] instructionTargeter, InstructionHandle instructionHandle, InstructionHandle InstructionHandleDP) {
		if (instructionTargeter != null) {
			for (int i = 0; i < instructionTargeter.length; i++) {
				instructionTargeter[i].updateTarget(instructionHandle, InstructionHandleDP);
			}
		}
	}
}
