package org.apache.bcel.generic;

public class D2I extends ConversionInstruction {
	private static final long serialVersionUID = -1226710355146064416L;

	public D2I() {
		super((short) 142);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitD2I(this);
	}
}
