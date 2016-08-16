package org.apache.bcel.generic;

public class FMUL extends ArithmeticInstruction {
	private static final long serialVersionUID = -1981818566663824251L;

	public FMUL() {
		super((short) 106);
	}

	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitFMUL(this);
	}
}
