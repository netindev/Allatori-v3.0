package org.apache.bcel.generic;

public class ALOAD extends LoadInstruction {
	private static final long serialVersionUID = 6993893925210913542L;

	ALOAD() {
		super((short) 25, (short) 42);
	}

	public ALOAD(int n) {
		super((short) 25, (short) 42, n);
	}

	public void accept(Visitor v) {
		super.accept(v);
		v.visitALOAD(this);
	}
}
