package org.apache.bcel.generic;

public class IF_ICMPLT extends IfInstruction {
	private static final long serialVersionUID = -6835991395337462478L;

	IF_ICMPLT() {
	}

	public IF_ICMPLT(InstructionHandle target) {
		super((short) 161, target);
	}

	public IfInstruction negate() {
		return new IF_ICMPGE(target);
	}

	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIF_ICMPLT(this);
	}
}
