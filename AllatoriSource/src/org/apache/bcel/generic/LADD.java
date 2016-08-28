package org.apache.bcel.generic;

public class LADD extends ArithmeticInstruction {
	private static final long serialVersionUID = 2093272772688460551L;

	public LADD() {
		super((short) 97);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitLADD(this);
	}
}
