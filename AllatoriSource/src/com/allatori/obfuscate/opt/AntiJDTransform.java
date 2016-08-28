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

	private final ClassStorage classes;

	@Override
	public void execute(ClassGen _cg) {
		final int type = AntiJD.getType(this.classes, _cg);
		if (type != 0) {
			try {
				for (final Method method : _cg.getMethods()) {
					if (method.getCode() != null) {
						final MethodGen _mg = InitUtils.createMethodGen(method, _cg.getClassName(),
								_cg.getConstantPool(), _cg.getConstantPool().getConstantPool());
						final InstructionList _il = _mg.getInstructionList();
						InstructionHandle currentHandle = _il.getStart();
						while (currentHandle != null) {
							if (currentHandle.getInstruction() instanceof NEWARRAY
									|| currentHandle.getInstruction() instanceof ANEWARRAY) {
								InstructionHandle h = _il.append(currentHandle, new PUSH(_cg.getConstantPool(), 1));
								h = _il.append(h, new DUP());
								_il.append(h, new POP2());
								currentHandle = currentHandle.getNext().getNext().getNext().getNext();
							} else if (type == 2 && (currentHandle.getInstruction() instanceof BIPUSH
									|| currentHandle.getInstruction() instanceof SIPUSH
									|| currentHandle.getInstruction() instanceof ICONST)) {
								InstructionHandle h = _il.append(currentHandle, new PUSH(_cg.getConstantPool(), 1));
								h = _il.append(h, new DUP());
								_il.append(h, new POP2());
								currentHandle = currentHandle.getNext().getNext().getNext().getNext();
							} else {
								currentHandle = currentHandle.getNext();
							}
						}
						_mg.setMaxStack();
						_mg.setMaxLocals();
						_cg.replaceMethod(method, _mg.getMethod());
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
	}

	public AntiJDTransform(ClassStorage var1) {
		this.classes = var1;
	}

	@Override
	public void terminate() {
	}
}
