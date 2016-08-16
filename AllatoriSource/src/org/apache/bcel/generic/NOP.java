package org.apache.bcel.generic;

public class NOP extends Instruction {
	private static final long serialVersionUID = -244116825309415153L;

	public NOP() {
		super((short) 0, (short) 1);
	}

	public void accept(Visitor v) {
		v.visitNOP(this);
	}
}
