package org.apache.bcel.generic;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantLong;

public class LDC2_W extends CPInstruction implements PushInstruction {
	private static final long serialVersionUID = 7403326732924539892L;

	LDC2_W() {
	}

	public LDC2_W(int index) {
		super((short) 20, index);
	}

	public Type getType(ConstantPoolGen cpg) {
		switch (cpg.getConstantPool().getConstant(index).getTag()) {
		case 5:
			return Type.LONG;
		case 6:
			return Type.DOUBLE;
		default:
			throw new RuntimeException(new StringBuilder().append("Unknown constant type ").append(opcode).toString());
		}
	}

	public Number getValue(ConstantPoolGen cpg) {
		Constant c = cpg.getConstantPool().getConstant(index);
		switch (c.getTag()) {
		case 5:
			return Long.valueOf(((ConstantLong) c).getBytes());
		case 6:
			return new Double(((ConstantDouble) c).getBytes());
		default:
			throw new RuntimeException(
					new StringBuilder().append("Unknown or invalid constant type at ").append(index).toString());
		}
	}

	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitPushInstruction(this);
		v.visitTypedInstruction(this);
		v.visitCPInstruction(this);
		v.visitLDC2_W(this);
	}
}
