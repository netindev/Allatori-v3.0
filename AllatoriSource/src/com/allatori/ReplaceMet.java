package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

public class ReplaceMet implements ObfuscationType {
	
	/* OK */

	private final ClassStorage classStorage;

	public ReplaceMet(ClassStorage classStorage) {
		this.classStorage = classStorage;
	}

	@Override
	public void terminate() {
		/* empty */
	}

	@Override
	public void execute(ClassGen classGen) {
		final String className = ClassUtils.getClassName();
		final String methodName = ClassUtils.getMethodName();
		if (className != null && methodName != null) {
			if (ClassUtils.getClassConstraint().apply(this.classStorage, classGen)) {
				try {
					final Method[] methods = classGen.getMethods();
					for (int i = 0; i < methods.length; i++) {
						Method method = methods[i];
						if (method.getCode() != null && ClassUtils.getClassConstraint().apply(method)) {
							final MethodGen methodGen = InitUtils.createMethodGen(method, classGen.getClassName(),
									classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
							final InstructionFactory instructionFactory = new InstructionFactory(classGen);
							InstructionList instructionList = methodGen.getInstructionList();
							final InstructionHandle instructionHandle = instructionList.getEnd();
							InstructionHandle tempHandle;
							if (ClassUtils.getPassThis()) {
								tempHandle = instructionList.insert(instructionHandle, instructionFactory.createInvoke(
										className, methodName, Type.VOID, new Type[] { Type.OBJECT }, (short) 184));
								tempHandle = instructionList.insert(tempHandle, InstructionFactory.createThis());
							} else {
								tempHandle = instructionList.insert(instructionHandle, instructionFactory
										.createInvoke(className, methodName, Type.VOID, Type.NO_ARGS, (short) 184));
							}
							InstructionTargeter[] instructionTargeterArr = instructionHandle.getTargeters();
							for (int j = instructionTargeterArr.length - 1; j >= 0; j--) {
								instructionTargeterArr[j].updateTarget(instructionHandle, tempHandle);
							}
							methodGen.setMaxStack();
							classGen.replaceMethod(method, methodGen.getMethod());
						}
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
