package org.apache.bcel.generic;

public class ACONST_NULL extends Instruction implements PushInstruction, TypedInstruction {
	private static final long serialVersionUID = -4127036801984829715L;

	public ACONST_NULL() {
		super((short) 1, (short) 1);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.NULL;
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitPushInstruction(this);
		v.visitTypedInstruction(this);
		v.visitACONST_NULL(this);
	}
}
