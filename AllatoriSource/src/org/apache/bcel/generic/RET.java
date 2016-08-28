package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.util.ByteSequence;

public class RET extends Instruction implements IndexedInstruction, TypedInstruction {
	private static final long serialVersionUID = -3751746568458761719L;
	private boolean wide;
	private int index;

	RET() {
	}

	public RET(int index) {
		super((short) 169, (short) 2);
		setIndex(index);
	}

	@Override
	public void dump(DataOutputStream out) throws IOException {
		if (wide)
			out.writeByte(196);
		out.writeByte(opcode);
		if (wide)
			out.writeShort(index);
		else
			out.writeByte(index);
	}

	private final void setWide() {
		wide = index > 255;
		if (wide)
			length = (short) 4;
		else
			length = (short) 2;
	}

	@Override
	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
		this.wide = wide;
		if (wide) {
			index = bytes.readUnsignedShort();
			length = (short) 4;
		} else {
			index = bytes.readUnsignedByte();
			length = (short) 2;
		}
	}

	@Override
	public final int getIndex() {
		return index;
	}

	@Override
	public final void setIndex(int n) {
		if (n < 0)
			throw new ClassGenException(new StringBuilder().append("Negative index value: ").append(n).toString());
		index = n;
		setWide();
	}

	@Override
	public String toString(boolean verbose) {
		return new StringBuilder().append(super.toString(verbose)).append(" ").append(index).toString();
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return ReturnaddressType.NO_TARGET;
	}

	@Override
	public void accept(Visitor v) {
		v.visitRET(this);
	}
}
