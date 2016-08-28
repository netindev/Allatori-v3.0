package org.apache.bcel.generic;

public class FASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = -1583134120388207470L;

	public FASTORE() {
		super((short) 81);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitFASTORE(this);
	}
}
