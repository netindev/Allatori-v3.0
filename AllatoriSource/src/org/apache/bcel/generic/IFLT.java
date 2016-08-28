package org.apache.bcel.generic;

public class IFLT extends IfInstruction {
	private static final long serialVersionUID = 2489268758129304231L;

	IFLT() {
	}

	public IFLT(InstructionHandle target) {
		super((short) 155, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFGE(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFLT(this);
	}
}
