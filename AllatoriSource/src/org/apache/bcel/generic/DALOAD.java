package org.apache.bcel.generic;

public class DALOAD extends ArrayInstruction implements StackProducer {
	private static final long serialVersionUID = -8493938101832015094L;

	public DALOAD() {
		super((short) 49);
	}

	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitDALOAD(this);
	}
}
