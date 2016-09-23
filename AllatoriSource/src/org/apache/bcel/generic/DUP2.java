package org.apache.bcel.generic;

public class DUP2 extends StackInstruction implements PushInstruction {
	private static final long serialVersionUID = -7098411152822612791L;

	public DUP2() {
		super((short) 92);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitPushInstruction(this);
		v.visitStackInstruction(this);
		v.visitDUP2(this);
	}
}
