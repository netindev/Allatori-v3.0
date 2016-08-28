package org.apache.bcel.generic;

public class DNEG extends ArithmeticInstruction {
	private static final long serialVersionUID = -8860107731099493429L;

	public DNEG() {
		super((short) 119);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitDNEG(this);
	}
}
