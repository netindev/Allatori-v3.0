/* LOOKUPSWITCH - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class LOOKUPSWITCH extends Select {
	private static final long serialVersionUID = -8263209830709467636L;

	LOOKUPSWITCH() {
		/* empty */
	}

	public LOOKUPSWITCH(int[] match, InstructionHandle[] targets, InstructionHandle defaultTarget) {
		super((short) 171, match, targets, defaultTarget);
		length = (short) (9 + match_length * 8);
		fixed_length = length;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		super.dump(out);
		out.writeInt(match_length);
		for (int i = 0; i < match_length; i++) {
			out.writeInt(match[i]);
			out.writeInt(indices[i] = getTargetOffset(targets[i]));
		}
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		super.initFromFile(bytes, wide);
		match_length = bytes.readInt();
		fixed_length = (short) (9 + match_length * 8);
		length = (short) (fixed_length + padding);
		match = new int[match_length];
		indices = new int[match_length];
		targets = new InstructionHandle[match_length];
		for (int i = 0; i < match_length; i++) {
			match[i] = bytes.readInt();
			indices[i] = bytes.readInt();
		}
	}

	@Override
	public void accept(Visitor v) {
		v.visitVariableLengthInstruction(this);
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitSelect(this);
		v.visitLOOKUPSWITCH(this);
	}
}
