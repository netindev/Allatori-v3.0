package org.apache.bcel.generic;

public class CALOAD extends ArrayInstruction implements StackProducer {
	private static final long serialVersionUID = -2085721349377637959L;

	public CALOAD() {
		super((short) 52);
	}

	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitCALOAD(this);
	}
}
