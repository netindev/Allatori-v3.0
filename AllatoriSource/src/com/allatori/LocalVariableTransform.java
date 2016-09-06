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
			Method actualMethod = methods[i];
			if (actualMethod.getCode() != null) {
				MethodGen methodGen = InitUtils.createMethodGen(actualMethod, classGen.getClassName(),
						classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
				InstructionList instructionList = methodGen.getInstructionList();
				instructionList.setPositions();
				for (InstructionHandle instructionHandle = instructionList
						.getStart(); instructionHandle != null; instructionHandle.getNext()) {
					if (instructionHandle.getInstruction() instanceof StoreInstruction
							&& instructionHandle.getNext() != null
							&& instructionHandle.getNext().getInstruction() instanceof LoadInstruction) {
						final InstructionHandle next = instructionHandle.getNext();
						final StoreInstruction storeInstruction = (StoreInstruction) instructionHandle.getInstruction();
						final LoadInstruction loadInstruction = (LoadInstruction) next.getInstruction();
						if (storeInstruction.getIndex() == loadInstruction.getIndex()
								&& this.instanceLineNumberGenOrLocalVariableGen(next.getTargeters())
								&& this.instanceLineNumberGenOrLocalVariableGen(instructionHandle.getTargeters())) {
							InstructionHandle nextInstruction = next.getNext();
							if (this.localVariableWithSameValueNOTExist(instructionList, instructionHandle, next)) {
								if (nextInstruction != null) {
									this.updateTargeters(instructionHandle.getTargeters(), instructionHandle,
											nextInstruction);
									this.updateTargeters(next.getTargeters(), next, nextInstruction);
									try {
										instructionList.delete(storeInstruction);
									} catch (final TargetLostException var16) {
										this.updateTargeters(var16, nextInstruction);
									}
									try {
										instructionList.delete(loadInstruction);
									} catch (final TargetLostException e) {
										this.updateTargeters(e, nextInstruction);
									}
								}
							} else {
								if (!(storeInstruction instanceof ASTORE) && !(storeInstruction instanceof FSTORE)
										&& !(storeInstruction instanceof ISTORE)) {
									if (storeInstruction instanceof DSTORE || storeInstruction instanceof LSTORE) {
										instructionList.insert(instructionHandle, new DUP2());
									}
								} else {
									instructionList.insert(instructionHandle, new DUP());
								}
								if ((nextInstruction = next.getNext()) != null) {
									this.updateTargeters(next.getTargeters(), next, nextInstruction);
									try {
										instructionList.delete(next);
									} catch (final TargetLostException e) {
										this.updateTargeters(e, nextInstruction);
									}
								}
							}
						}
					}
				}
				methodGen.setMaxStack();
				classGen.replaceMethod(actualMethod, methodGen.getMethod());
			}
		}

	}

	private void updateTargeters(TargetLostException targetLostException, InstructionHandle instructionHandle) {
		final InstructionHandle[] instructionHandleArray = targetLostException.getTargets();
		for (int i = 0; i < instructionHandleArray.length; i++) {
			final InstructionTargeter[] InstructionTargeterArray = instructionHandleArray[i].getTargeters();
			for (i = 0; i < InstructionTargeterArray.length; i++) {
				InstructionTargeterArray[i].updateTarget(instructionHandleArray[i], instructionHandle);
				++i;
			}
		}
	}

	private boolean instanceLineNumberGenOrLocalVariableGen(InstructionTargeter[] instructionTargeterArray) {
		if (instructionTargeterArray == null) {
			return true;
		} else {
			for (int i = 0; i < instructionTargeterArray.length; i++) {
				InstructionTargeter actualInstructionTargeter = instructionTargeterArray[i];
				if (!(actualInstructionTargeter instanceof LineNumberGen)
						&& !(actualInstructionTargeter instanceof LocalVariableGen)) {
					return false;
				}
			}
			return true;
		}
	}

	private boolean localVariableWithSameValueNOTExist(InstructionList instructionList,
			InstructionHandle instructionHandleIns, InstructionHandle instructionHandleNotEq) {
		final int index = ((LocalVariableInstruction) instructionHandleIns.getInstruction()).getIndex();
		for (InstructionHandle instructionHandleC = instructionList
				.getStart(); instructionHandleC != null; instructionHandleC.getNext()) {
			Instruction ins = instructionHandleC.getInstruction();
			if (ins instanceof LocalVariableInstruction && ((LocalVariableInstruction) ins).getIndex() == index
					&& instructionHandleC != instructionHandleIns && instructionHandleC != instructionHandleNotEq) {
				return false;
			}
		}
		return true;
	}

	private void updateTargeters(InstructionTargeter[] instructionTargeterArray, InstructionHandle instructionHandleUp,
			InstructionHandle instructionHandleT) {
		if (instructionTargeterArray != null) {
			for (int i = 0; i < instructionTargeterArray.length; i++) {
				instructionTargeterArray[i].updateTarget(instructionHandleUp, instructionHandleT);
			}
		}
	}
}
