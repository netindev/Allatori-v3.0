package org.apache.bcel.generic;

public class POP2 extends StackInstruction implements PopInstruction {
	private static final long serialVersionUID = -3868598204285850458L;

	public POP2() {
		super((short) 88);
	}

	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitPopInstruction(this);
		v.visitStackInstruction(this);
		v.visitPOP2(this);
	}
}
