package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class JSR extends JsrInstruction implements VariableLengthInstruction {
	private static final long serialVersionUID = 7425681395340093184L;

	JSR() {
	}

	public JSR(InstructionHandle target) {
		super((short) 168, target);
	}

	public void dump(DataOutputStream out) throws IOException {
		index = getTargetOffset();
		if (opcode == 168)
			super.dump(out);
		else {
			index = getTargetOffset();
			out.writeByte(opcode);
			out.writeInt(index);
		}
	}

	protected int updatePosition(int offset, int max_offset) {
		int i = getTargetOffset();
		position += offset;
		if (Math.abs(i) >= 32767 - max_offset) {
			opcode = (short) 201;
			short old_length = length;
			length = (short) 5;
			return length - old_length;
		}
		return 0;
	}

	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitVariableLengthInstruction(this);
		v.visitBranchInstruction(this);
		v.visitJsrInstruction(this);
		v.visitJSR(this);
	}
}
