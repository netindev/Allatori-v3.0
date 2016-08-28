package org.apache.bcel.generic;

public class IF_ACMPEQ extends IfInstruction {
	private static final long serialVersionUID = -4528733406576671849L;

	IF_ACMPEQ() {
	}

	public IF_ACMPEQ(InstructionHandle target) {
		super((short) 165, target);
	}

	@Override
	public IfInstruction negate() {
		return new IF_ACMPNE(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIF_ACMPEQ(this);
	}
}
