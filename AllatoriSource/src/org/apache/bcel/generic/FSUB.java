package org.apache.bcel.generic;

public class FSUB extends ArithmeticInstruction {
	private static final long serialVersionUID = 8403880233375858501L;

	public FSUB() {
		super((short) 102);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitFSUB(this);
	}
}
