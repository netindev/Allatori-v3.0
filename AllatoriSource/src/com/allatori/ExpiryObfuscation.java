package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NOP;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.SWAP;
import org.apache.bcel.generic.Type;

public class ExpiryObfuscation implements ObfuscationType {

	private final ClassStorage classStorage;

	@Override
	public void execute(ClassGen classGen) {
		if (Class64.getDate() != null) {
			if (Class64.getClassConstraint().apply(this.classStorage, classGen)) {
				try {
					final Method[] methodArr = classGen.getMethods();
					for (int i = 0; i < methodArr.length; i++) {
						final Method actualMethod = methodArr[i];
						if (actualMethod.getCode() != null && Class64.getClassConstraint().apply(actualMethod)) {
							final MethodGen methodGen = InitUtils.createMethodGen(actualMethod, classGen.getClassName(),
									classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
							final InstructionFactory instructionFactory = new InstructionFactory(classGen);
							final InstructionList instructionList0 = methodGen.getInstructionList();
							InstructionList instructionList1 = null;
							if (Tuning.isWeakStringEncryption()) {
								instructionList1 = this.method830(classGen.getConstantPool(), instructionFactory);
							} else {
								instructionList1 = this.method831(classGen.getConstantPool(), instructionFactory);
							}
							if ("<init>".equals(actualMethod.getName())) {
								final InstructionHandle instructionHandle0 = instructionList0.getEnd();
								final InstructionHandle instructionHandle1 = instructionList0.insert(instructionHandle0, instructionList1);
								final InstructionTargeter[] instructionTargeter = instructionHandle0.getTargeters();
								for (int j = instructionTargeter.length - 1; j >= 0; j--) {
									final InstructionTargeter actualInstructionTargeter = instructionTargeter[j];
									actualInstructionTargeter.updateTarget(instructionHandle0, instructionHandle1);
								}
							} else {
								instructionList0.insert(instructionList1);
							}
							methodGen.setMaxStack();
							methodGen.setMaxLocals();
							classGen.replaceMethod(actualMethod, methodGen.getMethod());
							instructionList1.dispose();
						}
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ExpiryObfuscation(ClassStorage classStorage) {
		this.classStorage = classStorage;
	}

	private InstructionList method830(ConstantPoolGen constantPoolGen, InstructionFactory instructionFactory) {
		InstructionList instructionList = new InstructionList();
		instructionList.append(instructionFactory.createNew("java.util.Date"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, Class64.getDate().getTime()));
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "<init>", Type.VOID, new Type[] { Type.LONG }, (short) 183));
		instructionList.append(instructionFactory.createNew("java.util.Date"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		instructionList.append(InstructionConstants.LCONST_0);
		instructionList.append(InstructionConstants.DUP2_X2);
		instructionList.append(InstructionConstants.POP2);
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "getTime", Type.LONG, Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.DUP2_X1);
		instructionList.append(InstructionConstants.POP2);
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "getTime", Type.LONG, Type.NO_ARGS, (short) 182));
		instructionList.append(InstructionConstants.LSUB);
		instructionList.append(InstructionConstants.LCMP);
		BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 156, null);
		instructionList.append(branchInstruction);
		instructionList.append(instructionFactory.createNew("java.lang.Throwable"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, Class64.getString()));
		instructionList.append(instructionFactory.createInvoke("java.lang.Throwable", "<init>", Type.VOID, new Type[] { Type.STRING }, (short) 183));
		instructionList.append(InstructionConstants.ATHROW);
		InstructionHandle instructionHandle = instructionList.append(new NOP());
		branchInstruction.setTarget(instructionHandle);
		return instructionList;
	}

	private InstructionList method831(ConstantPoolGen constantPoolGen, InstructionFactory instructionFactory) {
		InstructionList instructionList = new InstructionList();
		instructionList.append(instructionFactory.createNew("java.util.Date"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, Class64.getDate().getTime()));
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "<init>", Type.VOID, new Type[] { Type.LONG }, (short) 183));
		instructionList.append(instructionFactory.createNew("java.util.Date"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		instructionList.append(new SWAP());
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "after", Type.BOOLEAN,new Type[] { new ObjectType("java.util.Date") }, (short) 182));
		BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 153, null);
		instructionList.append(branchInstruction);
		instructionList.append(instructionFactory.createNew("java.lang.Throwable"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, Class64.getString()));
		instructionList.append(instructionFactory.createInvoke("java.lang.Throwable", "<init>", Type.VOID, new Type[] { Type.STRING }, (short) 183));
		instructionList.append(InstructionConstants.ATHROW);
		InstructionHandle instructionHandle = instructionList.append(new NOP());
		branchInstruction.setTarget(instructionHandle);
		return instructionList;
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
