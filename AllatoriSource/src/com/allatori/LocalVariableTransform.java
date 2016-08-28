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

	@Override
	public void patch(ClassGen var1) {
		final Method[] methods = var1.getMethods();
		int var3;
		for (int var10000 = var3 = 0; var10000 < methods.length; var10000 = var3) {
			Method method;
			if ((method = methods[var3]).getCode() != null) {
				MethodGen methodGen;
				InstructionList il;
				(il = (methodGen = InitUtils.createMethodGen(method, var1.getClassName(), var1.getConstantPool(),
						var1.getConstantPool().getConstantPool())).getInstructionList()).setPositions();

				InstructionHandle current;
				for (InstructionHandle var17 = current = il.getStart(); var17 != null; var17 = current = current
						.getNext()) {
					if (current.getInstruction() instanceof StoreInstruction && current.getNext() != null
							&& current.getNext().getInstruction() instanceof LoadInstruction) {
						final InstructionHandle next = current.getNext();
						final StoreInstruction var10 = (StoreInstruction) current.getInstruction();
						final LoadInstruction var11 = (LoadInstruction) next.getInstruction();
						if (var10.getIndex() == var11.getIndex() && this.method1401(next.getTargeters())
								&& this.method1401(current.getTargeters())) {
							InstructionHandle nextNext;
							if (this.localVariableWithSameValueNOTExist(il, current, next)) {
								if ((nextNext = next.getNext()) != null) {
									this.updateTargeters(current.getTargeters(), current, nextNext);
									this.updateTargeters(next.getTargeters(), next, nextNext);

									try {
										il.delete(var10);
									} catch (final TargetLostException var16) {
										this.updateTargeters(var16, nextNext);
									}

									try {
										il.delete(var11);
									} catch (final TargetLostException var15) {
										this.updateTargeters(var15, nextNext);
									}
								}
							} else {
								if (!(var10 instanceof ASTORE) && !(var10 instanceof FSTORE)
										&& !(var10 instanceof ISTORE)) {
									if (var10 instanceof DSTORE || var10 instanceof LSTORE) {
										il.insert(current, new DUP2());
									}
								} else {
									il.insert(current, new DUP());
								}

								if ((nextNext = next.getNext()) != null) {
									this.updateTargeters(next.getTargeters(), next, nextNext);

									try {
										il.delete(next);
									} catch (final TargetLostException var14) {
										this.updateTargeters(var14, nextNext);
									}
								}
							}
						}
					}
				}

				methodGen.setMaxStack();
				var1.replaceMethod(method, methodGen.getMethod());
			}

			++var3;
		}

	}

	private void updateTargeters(TargetLostException var1, InstructionHandle var2) {
		final InstructionHandle[] var3 = var1.getTargets();

		int var4;
		for (int var10000 = var4 = 0; var10000 < var3.length; var10000 = var4) {
			final InstructionTargeter[] var5 = var3[var4].getTargeters();

			int var6;
			for (var10000 = var6 = 0; var10000 < var5.length; var10000 = var6) {
				var5[var6].updateTarget(var3[var4], var2);
				++var6;
			}

			++var4;
		}

	}

	private boolean method1401(InstructionTargeter[] var1) {
		if (var1 == null) {
			return true;
		} else {
			int var2;
			for (int var10000 = var2 = 0; var10000 < var1.length; var10000 = var2) {
				InstructionTargeter var3;
				if (!((var3 = var1[var2]) instanceof LineNumberGen) && !(var3 instanceof LocalVariableGen)) {
					return false;
				}

				++var2;
			}

			return true;
		}
	}

	private boolean localVariableWithSameValueNOTExist(InstructionList var1, InstructionHandle var2,
			InstructionHandle var3) {
		final int value1 = ((LocalVariableInstruction) var2.getInstruction()).getIndex();

		InstructionHandle var5;
		for (InstructionHandle var10000 = var5 = var1.getStart(); var10000 != null; var10000 = var5 = var5.getNext()) {
			Instruction ins;
			if ((ins = var5.getInstruction()) instanceof LocalVariableInstruction
					&& ((LocalVariableInstruction) ins).getIndex() == value1 && var5 != var2 && var5 != var3) {
				return false;
			}
		}

		return true;
	}

	private void updateTargeters(InstructionTargeter[] var1, InstructionHandle var2, InstructionHandle var3) {
		if (var1 != null) {
			int var4;
			for (int var10000 = var4 = 0; var10000 < var1.length; var10000 = var4) {
				var1[var4].updateTarget(var2, var3);
				++var4;
			}

		}
	}
}
