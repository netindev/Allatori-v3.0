package org.apache.bcel.generic;

public class FADD extends ArithmeticInstruction {
	private static final long serialVersionUID = 5654582990564566355L;

	public FADD() {
		super((short) 98);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitFADD(this);
	}
}
