package org.apache.bcel.generic;

public class SWAP extends StackInstruction implements StackConsumer, StackProducer {
	private static final long serialVersionUID = 1965736870393433312L;

	public SWAP() {
		super((short) 95);
	}

	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitStackProducer(this);
		v.visitStackInstruction(this);
		v.visitSWAP(this);
	}
}
