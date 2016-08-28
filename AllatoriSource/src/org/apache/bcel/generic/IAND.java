package org.apache.bcel.generic;

public class IAND extends ArithmeticInstruction {
	private static final long serialVersionUID = -3190292062305201816L;

	public IAND() {
		super((short) 126);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitIAND(this);
	}
}
