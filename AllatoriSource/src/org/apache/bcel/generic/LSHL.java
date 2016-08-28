package org.apache.bcel.generic;

public class LSHL extends ArithmeticInstruction {
	private static final long serialVersionUID = 7855322471731877312L;

	public LSHL() {
		super((short) 121);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitLSHL(this);
	}
}
