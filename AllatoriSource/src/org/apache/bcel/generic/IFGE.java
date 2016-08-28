package org.apache.bcel.generic;

public class IFGE extends IfInstruction {
	private static final long serialVersionUID = 8975527282985945729L;

	IFGE() {
	}

	public IFGE(InstructionHandle target) {
		super((short) 156, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFLT(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFGE(this);
	}
}
