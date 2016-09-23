package com.allatori.obfuscate.opt;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ANEWARRAY;
import org.apache.bcel.generic.BIPUSH;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NEWARRAY;
import org.apache.bcel.generic.POP2;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.SIPUSH;

import com.allatori.ClassStorage;
import com.allatori.InitUtils;
import com.allatori.ObfuscationType;

public class AntiJDTransform implements ObfuscationType {
	
	/* OK */

	private final ClassStorage classes;

	@Override
	public void execute(ClassGen classGen) {
		final int type = AntiJD.getType(this.classes, classGen);
		if (type != 0) {
			try {
				for (final Method method : classGen.getMethods()) {
					if (method.getCode() != null) {
						final MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(),
								classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
						final InstructionList instructionList = methodGen.getInstructionList();
						InstructionHandle currentHandle = instructionList.getStart();
						while (currentHandle != null) {
							if (currentHandle.getInstruction() instanceof NEWARRAY
									|| currentHandle.getInstruction() instanceof ANEWARRAY) {
								InstructionHandle instructionHandle = instructionList.append(currentHandle, new PUSH(classGen.getConstantPool(), 1));
								instructionHandle = instructionList.append(instructionHandle, new DUP());
								instructionList.append(instructionHandle, new POP2());
								currentHandle = currentHandle.getNext().getNext().getNext().getNext();
							} else if (type == 2 && (currentHandle.getInstruction() instanceof BIPUSH
									|| currentHandle.getInstruction() instanceof SIPUSH
									|| currentHandle.getInstruction() instanceof ICONST)) {
								InstructionHandle instructionHandle = instructionList.append(currentHandle, new PUSH(classGen.getConstantPool(), 1));
								instructionHandle = instructionList.append(instructionHandle, new DUP());
								instructionList.append(instructionHandle, new POP2());
								currentHandle = currentHandle.getNext().getNext().getNext().getNext();
							} else {
								currentHandle = currentHandle.getNext();
							}
						}
						methodGen.setMaxStack();
						methodGen.setMaxLocals();
						classGen.replaceMethod(method, methodGen.getMethod());
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
	}

	public AntiJDTransform(ClassStorage classStorage) {
		this.classes = classStorage;
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
