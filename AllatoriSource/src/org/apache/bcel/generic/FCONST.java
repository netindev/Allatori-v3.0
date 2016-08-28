package org.apache.bcel.generic;

public class FCONST extends Instruction implements ConstantPushInstruction {
	private static final long serialVersionUID = 3018815844848018054L;
	private float value;

	FCONST() {
	}

	public FCONST(float f) {
		super((short) 11, (short) 1);
		if (f == 0.0)
			opcode = (short) 11;
		else if (f == 1.0)
			opcode = (short) 12;
		else if (f == 2.0)
			opcode = (short) 13;
		else
			throw new ClassGenException(
					new StringBuilder().append("FCONST can be used only for 0.0, 1.0 and 2.0: ").append(f).toString());
		value = f;
	}

	@Override
	public Number getValue() {
		return new Float(value);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.FLOAT;
	}

	@Override
	public void accept(Visitor v) {
		v.visitPushInstruction(this);
		v.visitStackProducer(this);
		v.visitTypedInstruction(this);
		v.visitConstantPushInstruction(this);
		v.visitFCONST(this);
	}
}
