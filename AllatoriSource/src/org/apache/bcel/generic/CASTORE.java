package org.apache.bcel.generic;

public class CASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = -4021755905444053495L;

	public CASTORE() {
		super((short) 85);
	}

	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitCASTORE(this);
	}
}
