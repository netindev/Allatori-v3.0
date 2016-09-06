/* GOTO_W - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class GOTO_W extends GotoInstruction {
	private static final long serialVersionUID = -344799540124265957L;

	GOTO_W() {
		/* empty */
	}

	public GOTO_W(InstructionHandle target) {
		super((short) 200, target);
		length = (short) 5;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		index = getTargetOffset();
		out.writeByte(opcode);
		out.writeInt(index);
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		index = bytes.readInt();
		length = (short) 5;
	}

	@Override
	public void accept(Visitor v) {
		v.visitUnconditionalBranch(this);
		v.visitBranchInstruction(this);
		v.visitGotoInstruction(this);
		v.visitGOTO_W(this);
	}
}
