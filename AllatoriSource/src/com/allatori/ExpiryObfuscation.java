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
		if (DateUtils.getDate() != null) {
			if (DateUtils.getClassConstraint().apply(this.classStorage, classGen)) {
				try {
					final Method[] methods = classGen.getMethods();
					for (int i = 0; i < methods.length; i++) {
						final Method actualMethod = methods[i];
						if (actualMethod.getCode() != null && DateUtils.getClassConstraint().apply(actualMethod)) {
							final MethodGen methodGen = InitUtils.createMethodGen(actualMethod, classGen.getClassName(),
									classGen.getConstantPool(), classGen.getConstantPool().getConstantPool());
							final InstructionFactory instructionFactory = new InstructionFactory(classGen);
							final InstructionList instructionList = methodGen.getInstructionList();
							InstructionList weakStringEnc = Tuning.isWeakStringEncryption()
									? this.method830(classGen.getConstantPool(), instructionFactory)
									: this.method831(classGen.getConstantPool(), instructionFactory);
							if ("<init>".equals(actualMethod.getName())) {
								final InstructionHandle insEnd = instructionList.getEnd();
								final InstructionHandle insIns = instructionList.insert(insEnd, weakStringEnc);
								final InstructionTargeter[] instructionTargeterArr = insEnd.getTargeters();
								for (int j = instructionTargeterArr.length - 1; j >= 0; j--) {
									final InstructionTargeter instructionTargeter = instructionTargeterArr[j];
									instructionTargeter.updateTarget(insEnd, insIns);
								}
							} else {
								instructionList.insert(weakStringEnc);
							}
							methodGen.setMaxStack();
							methodGen.setMaxLocals();
							classGen.replaceMethod(actualMethod, methodGen.getMethod());
							weakStringEnc.dispose();
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
		instructionList.append(new PUSH(constantPoolGen, DateUtils.getDate().getTime()));
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
		final BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 156, null);
		instructionList.append(branchInstruction);
		instructionList.append(instructionFactory.createNew("java.lang.Throwable"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, DateUtils.getString()));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.Throwable", "<init>", Type.VOID, new Type[] { Type.STRING }, (short) 183));
		instructionList.append(InstructionConstants.ATHROW);
		final InstructionHandle var5 = instructionList.append(new NOP());
		branchInstruction.setTarget(var5);
		return instructionList;
	}

	private InstructionList method831(ConstantPoolGen constantPoolGen, InstructionFactory instructionFactory) {
		InstructionList instructionList = new InstructionList();
		instructionList.append(instructionFactory.createNew("java.util.Date"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, DateUtils.getDate().getTime()));
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "<init>", Type.VOID, new Type[] { Type.LONG }, (short) 183));
		instructionList.append(instructionFactory.createNew("java.util.Date"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		instructionList.append(new SWAP());
		instructionList.append(instructionFactory.createInvoke("java.util.Date", "after", Type.BOOLEAN,
				new Type[] { new ObjectType("java.util.Date") }, (short) 182));
		final BranchInstruction branchInstruction = InstructionFactory.createBranchInstruction((short) 153, null);
		instructionList.append(branchInstruction);
		instructionList.append(instructionFactory.createNew("java.lang.Throwable"));
		instructionList.append(InstructionConstants.DUP);
		instructionList.append(new PUSH(constantPoolGen, DateUtils.getString()));
		instructionList.append(
				instructionFactory.createInvoke("java.lang.Throwable", "<init>", Type.VOID, new Type[] { Type.STRING }, (short) 183));
		instructionList.append(InstructionConstants.ATHROW);
		final InstructionHandle instructionHandle = instructionList.append(new NOP());
		branchInstruction.setTarget(instructionHandle);
		return instructionList;
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
