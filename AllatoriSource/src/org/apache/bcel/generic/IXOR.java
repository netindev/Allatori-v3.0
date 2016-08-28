package org.apache.bcel.generic;

public class IXOR extends ArithmeticInstruction {
	private static final long serialVersionUID = -6524415552072672532L;

	public IXOR() {
		super((short) 130);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitIXOR(this);
	}
}
