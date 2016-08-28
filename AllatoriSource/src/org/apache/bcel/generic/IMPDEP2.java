package org.apache.bcel.generic;

public class IMPDEP2 extends Instruction {
	private static final long serialVersionUID = 4097564761941607538L;

	public IMPDEP2() {
		super((short) 255, (short) 1);
	}

	@Override
	public void accept(Visitor v) {
		v.visitIMPDEP2(this);
	}
}
