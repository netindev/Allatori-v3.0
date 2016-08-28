package org.apache.bcel.generic;

public class INEG extends ArithmeticInstruction {
	private static final long serialVersionUID = 6175987548738672934L;

	public INEG() {
		super((short) 116);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitINEG(this);
	}
}
