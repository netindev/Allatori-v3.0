/* BIPUSH - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class BIPUSH extends Instruction implements ConstantPushInstruction {
	private static final long serialVersionUID = -6859389515217572656L;
	private byte b;

	BIPUSH() {
		/* empty */
	}

	public BIPUSH(byte b) {
		super((short) 16, (short) 2);
		this.b = b;
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		super.dump(out);
		out.writeByte(b);
	}

	@Override
	public String toString(boolean verbose) {
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(b).toString();
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		length = (short) 2;
		b = bytes.readByte();
	}

	@Override
	public Number getValue() {
		return Integer.valueOf(b);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.BYTE;
	}

	@Override
	public void accept(Visitor v) {
		v.visitPushInstruction(this);
		v.visitStackProducer(this);
		v.visitTypedInstruction(this);
		v.visitConstantPushInstruction(this);
		v.visitBIPUSH(this);
	}
}
