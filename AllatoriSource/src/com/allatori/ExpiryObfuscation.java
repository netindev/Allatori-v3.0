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

	private final ClassStorage aClassStorage_1144;

	@Override
	public void execute(ClassGen var1) {
		if (DateUtils.method746() != null) {
			if (DateUtils.method747().apply(this.aClassStorage_1144, var1)) {
				try {
					final Method[] var3 = var1.getMethods();
					int var4 = 0;

					for (int var10000 = var4; var10000 < var3.length; var10000 = var4) {
						final Method var5 = var3[var4];
						if (var5.getCode() != null && DateUtils.method747().apply(var5)) {
							final MethodGen var6 = InitUtils.createMethodGen(var5, var1.getClassName(),
									var1.getConstantPool(), var1.getConstantPool().getConstantPool());
							final InstructionFactory var7 = new InstructionFactory(var1);
							final InstructionList var8 = var6.getInstructionList();
							InstructionList var9;
							if (Tuning.isWeakStringEncryption()) {
								var9 = this.method830(var1.getConstantPool(), var7);
							} else {
								var9 = this.method831(var1.getConstantPool(), var7);
							}

							if ("<init>".equals(var5.getName())) {
								final InstructionHandle var10 = var8.getEnd();
								final InstructionHandle var11 = var8.insert(var10, var9);
								final InstructionTargeter[] var12 = var10.getTargeters();
								int var13 = var12.length - 1;

								for (var10000 = var13; var10000 >= 0; var10000 = var13) {
									final InstructionTargeter var14 = var12[var13];
									var14.updateTarget(var10, var11);
									--var13;
								}
							} else {
								var8.insert(var9);
							}

							var6.setMaxStack();
							var6.setMaxLocals();
							var1.replaceMethod(var5, var6.getMethod());
							var9.dispose();
						}

						++var4;
					}
				} catch (final Exception var15) {
					var15.printStackTrace();
				}

			}
		}
	}

	public ExpiryObfuscation(ClassStorage var1) {
		this.aClassStorage_1144 = var1;
	}

	private InstructionList method830(ConstantPoolGen var1, InstructionFactory var2) {
		InstructionList var3;
		(var3 = new InstructionList()).append(var2.createNew("java.util.Date"));
		var3.append(InstructionConstants.DUP);
		var3.append(new PUSH(var1, DateUtils.method746().getTime()));
		var3.append(var2.createInvoke("java.util.Date", "<init>", Type.VOID, new Type[] { Type.LONG }, (short) 183));
		var3.append(var2.createNew("java.util.Date"));
		var3.append(InstructionConstants.DUP);
		var3.append(var2.createInvoke("java.util.Date", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		var3.append(InstructionConstants.LCONST_0);
		var3.append(InstructionConstants.DUP2_X2);
		var3.append(InstructionConstants.POP2);
		var3.append(var2.createInvoke("java.util.Date", "getTime", Type.LONG, Type.NO_ARGS, (short) 182));
		var3.append(InstructionConstants.DUP2_X1);
		var3.append(InstructionConstants.POP2);
		var3.append(var2.createInvoke("java.util.Date", "getTime", Type.LONG, Type.NO_ARGS, (short) 182));
		var3.append(InstructionConstants.LSUB);
		var3.append(InstructionConstants.LCMP);
		final BranchInstruction var4 = InstructionFactory.createBranchInstruction((short) 156, null);
		var3.append(var4);
		var3.append(var2.createNew("java.lang.Throwable"));
		var3.append(InstructionConstants.DUP);
		var3.append(new PUSH(var1, DateUtils.method748()));
		var3.append(
				var2.createInvoke("java.lang.Throwable", "<init>", Type.VOID, new Type[] { Type.STRING }, (short) 183));
		var3.append(InstructionConstants.ATHROW);
		final InstructionHandle var5 = var3.append(new NOP());
		var4.setTarget(var5);
		return var3;
	}

	private InstructionList method831(ConstantPoolGen var1, InstructionFactory var2) {
		InstructionList var3;
		(var3 = new InstructionList()).append(var2.createNew("java.util.Date"));
		var3.append(InstructionConstants.DUP);
		var3.append(new PUSH(var1, DateUtils.method746().getTime()));
		var3.append(var2.createInvoke("java.util.Date", "<init>", Type.VOID, new Type[] { Type.LONG }, (short) 183));
		var3.append(var2.createNew("java.util.Date"));
		var3.append(InstructionConstants.DUP);
		var3.append(var2.createInvoke("java.util.Date", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
		var3.append(new SWAP());
		var3.append(var2.createInvoke("java.util.Date", "after", Type.BOOLEAN,
				new Type[] { new ObjectType("java.util.Date") }, (short) 182));
		final BranchInstruction var4 = InstructionFactory.createBranchInstruction((short) 153, null);
		var3.append(var4);
		var3.append(var2.createNew("java.lang.Throwable"));
		var3.append(InstructionConstants.DUP);
		var3.append(new PUSH(var1, DateUtils.method748()));
		var3.append(
				var2.createInvoke("java.lang.Throwable", "<init>", Type.VOID, new Type[] { Type.STRING }, (short) 183));
		var3.append(InstructionConstants.ATHROW);
		final InstructionHandle var5 = var3.append(new NOP());
		var4.setTarget(var5);
		return var3;
	}

	@Override
	public void terminate() {
	}
}
