package org.apache.bcel.generic;

public class IMPDEP1 extends Instruction {
	private static final long serialVersionUID = 134641616863598508L;

	public IMPDEP1() {
		super((short) 254, (short) 1);
	}

	@Override
	public void accept(Visitor v) {
		v.visitIMPDEP1(this);
	}
}
