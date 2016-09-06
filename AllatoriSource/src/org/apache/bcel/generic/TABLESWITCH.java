/* TABLESWITCH - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class TABLESWITCH extends Select {
	private static final long serialVersionUID = -1178229029789923698L;

	TABLESWITCH() {
		/* empty */
	}

	public TABLESWITCH(int[] match, InstructionHandle[] targets, InstructionHandle defaultTarget) {
		super((short) 170, match, targets, defaultTarget);
		length = (short) (13 + match_length * 4);
		fixed_length = length;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		super.dump(out);
		final int low = match_length > 0 ? match[0] : 0;
		out.writeInt(low);
		final int high = match_length > 0 ? match[match_length - 1] : 0;
		out.writeInt(high);
		for (int i = 0; i < match_length; i++)
			out.writeInt(indices[i] = getTargetOffset(targets[i]));
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		super.initFromFile(bytes, wide);
		final int low = bytes.readInt();
		final int high = bytes.readInt();
		match_length = high - low + 1;
		fixed_length = (short) (13 + match_length * 4);
		length = (short) (fixed_length + padding);
		match = new int[match_length];
		indices = new int[match_length];
		targets = new InstructionHandle[match_length];
		for (int i = 0; i < match_length; i++) {
			match[i] = low + i;
			indices[i] = bytes.readInt();
		}
	}

	@Override
	public void accept(Visitor v) {
		v.visitVariableLengthInstruction(this);
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitSelect(this);
		v.visitTABLESWITCH(this);
	}
}
