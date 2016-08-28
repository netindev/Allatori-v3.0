package org.apache.bcel.generic;

public class ISUB extends ArithmeticInstruction {
	private static final long serialVersionUID = 1878271684888856459L;

	public ISUB() {
		super((short) 100);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitISUB(this);
	}
}
