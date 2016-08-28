package org.apache.bcel.generic;

public class IALOAD extends ArrayInstruction implements StackProducer {
	private static final long serialVersionUID = -3275094472217586613L;

	public IALOAD() {
		super((short) 46);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitIALOAD(this);
	}
}
