package org.apache.bcel.generic;

public class DUP2_X1 extends StackInstruction {
	private static final long serialVersionUID = 4940667268525283202L;

	public DUP2_X1() {
		super((short) 93);
	}

	public void accept(Visitor v) {
		v.visitStackInstruction(this);
		v.visitDUP2_X1(this);
	}
}
