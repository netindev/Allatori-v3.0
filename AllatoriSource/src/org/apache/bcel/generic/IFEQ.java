package org.apache.bcel.generic;

public class IFEQ extends IfInstruction {
	private static final long serialVersionUID = -6140576561545855324L;

	IFEQ() {
	}

	public IFEQ(InstructionHandle target) {
		super((short) 153, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFNE(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFEQ(this);
	}
}
