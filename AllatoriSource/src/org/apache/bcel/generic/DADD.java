package org.apache.bcel.generic;

public class DADD extends ArithmeticInstruction {
	private static final long serialVersionUID = 241485501977646418L;

	public DADD() {
		super((short) 99);
	}

	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitDADD(this);
	}
}
