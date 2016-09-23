package org.apache.bcel.generic;

public class DRETURN extends ReturnInstruction {
	private static final long serialVersionUID = 7442064109402271402L;

	public DRETURN() {
		super((short) 175);
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackConsumer(this);
		v.visitReturnInstruction(this);
		v.visitDRETURN(this);
	}
}
