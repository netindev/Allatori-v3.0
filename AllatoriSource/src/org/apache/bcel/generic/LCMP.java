package org.apache.bcel.generic;

public class LCMP extends Instruction implements TypedInstruction, StackProducer, StackConsumer {
	private static final long serialVersionUID = 2981727810276161294L;

	public LCMP() {
		super((short) 148, (short) 1);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.LONG;
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitLCMP(this);
	}
}
