package org.apache.bcel.generic;

public class RETURN extends ReturnInstruction {
	private static final long serialVersionUID = -7375896559820981467L;

	public RETURN() {
		super((short) 177);
	}

	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackConsumer(this);
		v.visitReturnInstruction(this);
		v.visitRETURN(this);
	}
}
