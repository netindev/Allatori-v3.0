package org.apache.bcel.generic;

public class FCMPG extends Instruction implements TypedInstruction, StackProducer, StackConsumer {
	private static final long serialVersionUID = -715944337480121908L;

	public FCMPG() {
		super((short) 150, (short) 1);
	}

	public Type getType(ConstantPoolGen cp) {
		return Type.FLOAT;
	}

	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitFCMPG(this);
	}
}
