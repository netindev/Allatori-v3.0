/* JSR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class JSR extends JsrInstruction implements VariableLengthInstruction {
	private static final long serialVersionUID = 7425681395340093184L;

	JSR() {
		/* empty */
	}

	public JSR(InstructionHandle target) {
		super((short) 168, target);
	}

	@Override
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

	@Override
	protected int updatePosition(int offset, int max_offset) {
		final int i = getTargetOffset();
		position += offset;
		if (Math.abs(i) >= 32767 - max_offset) {
			opcode = (short) 201;
			final short old_length = length;
			length = (short) 5;
			return length - old_length;
		}
		return 0;
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitVariableLengthInstruction(this);
		v.visitBranchInstruction(this);
		v.visitJsrInstruction(this);
		v.visitJSR(this);
	}
}
