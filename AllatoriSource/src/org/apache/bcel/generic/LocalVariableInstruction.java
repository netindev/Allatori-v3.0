/* LocalVariableInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public abstract class LocalVariableInstruction extends Instruction implements TypedInstruction, IndexedInstruction {
	private static final long serialVersionUID = 8322269736316122743L;
	protected int n = -1;
	private short c_tag = -1;
	private short canon_tag = -1;

	private final boolean wide() {
		return n > 255;
	}

	LocalVariableInstruction(short canon_tag, short c_tag) {
		this.canon_tag = canon_tag;
		this.c_tag = c_tag;
	}

	LocalVariableInstruction() {
		/* empty */
	}

	protected LocalVariableInstruction(short opcode, short c_tag, int n) {
		super(opcode, (short) 2);
		this.c_tag = c_tag;
		canon_tag = opcode;
		setIndex(n);
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		if (wide())
			out.writeByte(196);
		out.writeByte(opcode);
		if (length > 1) {
			if (wide())
				out.writeShort(n);
			else
				out.writeByte(n);
		}
	}

	@Override
	public String toString(boolean verbose) {
		if (opcode >= 26 && opcode <= 45 || opcode >= 59 && opcode <= 78)
			return super.toString(verbose);
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(n).toString();
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		if (wide) {
			n = bytes.readUnsignedShort();
			length = (short) 4;
		} else if (opcode >= 21 && opcode <= 25 || opcode >= 54 && opcode <= 58) {
			n = bytes.readUnsignedByte();
			length = (short) 2;
		} else if (opcode <= 45) {
			n = (opcode - 26) % 4;
			length = (short) 1;
		} else {
			n = (opcode - 59) % 4;
			length = (short) 1;
		}
	}

	@Override
	public final int getIndex() {
		return n;
	}

	@Override
	public void setIndex(int n) {
		if (n < 0 || n > 65535)
			throw new ClassGenException(new StringBuilder().append("Illegal value: ").append(n).toString());
		this.n = n;
		if (n >= 0 && n <= 3) {
			opcode = (short) (c_tag + n);
			length = (short) 1;
		} else {
			opcode = canon_tag;
			if (wide())
				length = (short) 4;
			else
				length = (short) 2;
		}
	}

	public short getCanonicalTag() {
		return canon_tag;
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		switch (canon_tag) {
		case 21:
		case 54:
			return Type.INT;
		case 22:
		case 55:
			return Type.LONG;
		case 24:
		case 57:
			return Type.DOUBLE;
		case 23:
		case 56:
			return Type.FLOAT;
		case 25:
		case 58:
			return Type.OBJECT;
		default:
			throw new ClassGenException(
					new StringBuilder().append("Oops: unknown case in switch").append(canon_tag).toString());
		}
	}
}
