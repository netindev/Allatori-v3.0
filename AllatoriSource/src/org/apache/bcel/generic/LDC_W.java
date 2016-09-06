/* LDC_W - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class LDC_W extends LDC {
	private static final long serialVersionUID = -8040188785844554411L;

	LDC_W() {
		/* empty */
	}

	public LDC_W(int index) {
		super(index);
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		setIndex(bytes.readUnsignedShort());
		opcode = (short) 19;
		length = (short) 3;
	}
}
