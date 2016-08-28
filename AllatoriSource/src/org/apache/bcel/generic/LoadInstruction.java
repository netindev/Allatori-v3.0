package org.apache.bcel.generic;

public abstract class LoadInstruction extends LocalVariableInstruction implements PushInstruction {
	private static final long serialVersionUID = 3661924741022212247L;

	LoadInstruction(short canon_tag, short c_tag) {
		super(canon_tag, c_tag);
	}

	protected LoadInstruction(short opcode, short c_tag, int n) {
		super(opcode, c_tag, n);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitPushInstruction(this);
		v.visitTypedInstruction(this);
		v.visitLocalVariableInstruction(this);
		v.visitLoadInstruction(this);
	}
}
