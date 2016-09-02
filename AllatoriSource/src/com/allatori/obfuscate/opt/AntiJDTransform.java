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
	public void execute(ClassGen cg) {
		final int type = AntiJD.getType(this.classes, cg);
		if (type != 0) {
			try {
				for (final Method method : cg.getMethods()) {
					if (method.getCode() != null) {
						final MethodGen mg = InitUtils.createMethodGen(method, cg.getClassName(),
								cg.getConstantPool(), cg.getConstantPool().getConstantPool());
						final InstructionList il = mg.getInstructionList();
						InstructionHandle currentHandle = il.getStart();
						while (currentHandle != null) {
							if (currentHandle.getInstruction() instanceof NEWARRAY
									|| currentHandle.getInstruction() instanceof ANEWARRAY) {
								InstructionHandle h = il.append(currentHandle, new PUSH(cg.getConstantPool(), 1));
								h = il.append(h, new DUP());
								il.append(h, new POP2());
								currentHandle = currentHandle.getNext().getNext().getNext().getNext();
							} else if (type == 2 && (currentHandle.getInstruction() instanceof BIPUSH
									|| currentHandle.getInstruction() instanceof SIPUSH
									|| currentHandle.getInstruction() instanceof ICONST)) {
								InstructionHandle h = il.append(currentHandle, new PUSH(cg.getConstantPool(), 1));
								h = il.append(h, new DUP());
								il.append(h, new POP2());
								currentHandle = currentHandle.getNext().getNext().getNext().getNext();
							} else {
								currentHandle = currentHandle.getNext();
							}
						}
						mg.setMaxStack();
						mg.setMaxLocals();
						cg.replaceMethod(method, mg.getMethod());
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
	}

	public AntiJDTransform(ClassStorage cs) {
		this.classes = cs;
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
