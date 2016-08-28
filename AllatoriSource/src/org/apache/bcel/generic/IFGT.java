package org.apache.bcel.generic;

public class IFGT extends IfInstruction {
	private static final long serialVersionUID = -6271055211127179697L;

	IFGT() {
	}

	public IFGT(InstructionHandle target) {
		super((short) 157, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFLE(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFGT(this);
	}
}
