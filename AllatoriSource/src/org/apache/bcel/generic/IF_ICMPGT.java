package org.apache.bcel.generic;

public class IF_ICMPGT extends IfInstruction {
	private static final long serialVersionUID = -2569877744173094534L;

	IF_ICMPGT() {
	}

	public IF_ICMPGT(InstructionHandle target) {
		super((short) 163, target);
	}

	public IfInstruction negate() {
		return new IF_ICMPLE(target);
	}

	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIF_ICMPGT(this);
	}
}
