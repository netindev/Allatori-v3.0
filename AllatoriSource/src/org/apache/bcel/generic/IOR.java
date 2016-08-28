package org.apache.bcel.generic;

public class IOR extends ArithmeticInstruction {
	private static final long serialVersionUID = -6128609553204409153L;

	public IOR() {
		super((short) 128);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitIOR(this);
	}
}
