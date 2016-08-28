package org.apache.bcel.generic;

public class IFNONNULL extends IfInstruction {
	private static final long serialVersionUID = -6378085152112796792L;

	IFNONNULL() {
	}

	public IFNONNULL(InstructionHandle target) {
		super((short) 199, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFNULL(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFNONNULL(this);
	}
}
