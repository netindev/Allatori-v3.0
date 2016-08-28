package org.apache.bcel.generic;

public class DDIV extends ArithmeticInstruction {
	private static final long serialVersionUID = -2241740228269641540L;

	public DDIV() {
		super((short) 111);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitDDIV(this);
	}
}
