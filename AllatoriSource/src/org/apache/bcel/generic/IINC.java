package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class IINC extends LocalVariableInstruction {
	private static final long serialVersionUID = -8938364416647466869L;
	private boolean wide;
	private int c;

	IINC() {
	}

	public IINC(int n, int c) {
		opcode = (short) 132;
		length = (short) 3;
		setIndex(n);
		setIncrement(c);
	}

	public void dump(DataOutputStream out) throws IOException {
		if (wide)
			out.writeByte(196);
		out.writeByte(opcode);
		if (wide) {
			out.writeShort(n);
			out.writeShort(c);
		} else {
			out.writeByte(n);
			out.writeByte(c);
		}
	}

	private final void setWide() {
		wide = n > 255 || Math.abs(c) > 127;
		if (wide)
			length = (short) 6;
		else
			length = (short) 3;
	}

	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		this.wide = wide;
		if (wide) {
			length = (short) 6;
			n = bytes.readUnsignedShort();
			c = bytes.readShort();
		} else {
			length = (short) 3;
			n = bytes.readUnsignedByte();
			c = bytes.readByte();
		}
	}

	public String toString(boolean verbose) {
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(c).toString();
	}

	public final void setIndex(int n) {
		if (n < 0)
			throw new ClassGenException(new StringBuilder().append("Negative index value: ").append(n).toString());
		this.n = n;
		setWide();
	}

	public final int getIncrement() {
		return c;
	}

	public final void setIncrement(int c) {
		this.c = c;
		setWide();
	}

	public Type getType(ConstantPoolGen cp) {
		return Type.INT;
	}

	public void accept(Visitor v) {
		v.visitLocalVariableInstruction(this);
		v.visitIINC(this);
	}
}
