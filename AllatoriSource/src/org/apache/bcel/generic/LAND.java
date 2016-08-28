package org.apache.bcel.generic;

public class LAND extends ArithmeticInstruction {
	private static final long serialVersionUID = 2429077463161192057L;

	public LAND() {
		super((short) 127);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitLAND(this);
	}
}
