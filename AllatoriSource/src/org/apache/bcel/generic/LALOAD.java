package org.apache.bcel.generic;

public class LALOAD extends ArrayInstruction implements StackProducer {
	private static final long serialVersionUID = -3687594761485525620L;

	public LALOAD() {
		super((short) 47);
	}

	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitLALOAD(this);
	}
}
