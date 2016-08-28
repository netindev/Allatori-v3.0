package org.apache.bcel.generic;

public class SASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = -5187452948601765872L;

	public SASTORE() {
		super((short) 86);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitSASTORE(this);
	}
}
