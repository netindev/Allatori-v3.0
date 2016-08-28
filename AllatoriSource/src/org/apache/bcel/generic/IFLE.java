package org.apache.bcel.generic;

public class IFLE extends IfInstruction {
	private static final long serialVersionUID = 7916641859064664263L;

	IFLE() {
	}

	public IFLE(InstructionHandle target) {
		super((short) 158, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFGT(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFLE(this);
	}
}
