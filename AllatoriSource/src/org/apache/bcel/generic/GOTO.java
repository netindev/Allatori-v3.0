package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class GOTO extends GotoInstruction implements VariableLengthInstruction {
	private static final long serialVersionUID = 6106731367505145625L;

	GOTO() {
	}

	public GOTO(InstructionHandle target) {
		super((short) 167, target);
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		index = getTargetOffset();
		if (opcode == 167)
			super.dump(out);
		else {
			index = getTargetOffset();
			out.writeByte(opcode);
			out.writeInt(index);
		}
	}

	@Override
	protected int updatePosition(int offset, int max_offset) {
		final int i = getTargetOffset();
		position += offset;
		if (Math.abs(i) >= 32767 - max_offset) {
			opcode = (short) 200;
			final short old_length = length;
			length = (short) 5;
			return length - old_length;
		}
		return 0;
	}

	@Override
	public void accept(Visitor v) {
		v.visitVariableLengthInstruction(this);
		v.visitUnconditionalBranch(this);
		v.visitBranchInstruction(this);
		v.visitGotoInstruction(this);
		v.visitGOTO(this);
	}
}
