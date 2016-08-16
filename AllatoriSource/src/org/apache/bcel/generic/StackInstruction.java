package org.apache.bcel.generic;

public abstract class StackInstruction extends Instruction {
	private static final long serialVersionUID = -8542057001831958935L;

	StackInstruction() {
	}

	protected StackInstruction(short opcode) {
		super(opcode, (short) 1);
	}

	public Type getType(ConstantPoolGen cp) {
		return Type.UNKNOWN;
	}
}
