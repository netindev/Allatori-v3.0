package org.apache.bcel.generic;

public class F2D extends ConversionInstruction {
	private static final long serialVersionUID = -4668119344425861047L;

	public F2D() {
		super((short) 141);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitF2D(this);
	}
}
