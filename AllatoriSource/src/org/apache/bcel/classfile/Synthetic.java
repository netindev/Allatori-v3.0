package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Synthetic extends Attribute {
	private static final long serialVersionUID = -123334426995458366L;
	private byte[] bytes;

	public Synthetic(Synthetic c) {
		this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
	}

	public Synthetic(int name_index, int length, byte[] bytes, ConstantPool constant_pool) {
		super((byte) 7, name_index, length, constant_pool);
		this.bytes = bytes;
	}

	Synthetic(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, (byte[]) null, constant_pool);
		if (length > 0) {
			bytes = new byte[length];
			file.readFully(bytes);
			System.err.println("Synthetic attribute with length > 0");
		}
	}

	public void accept(Visitor v) {
		v.visitSynthetic(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		if (length > 0)
			file.write(bytes, 0, length);
	}

	public final byte[] getBytes() {
		return bytes;
	}

	public final void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder("Synthetic");
		if (length > 0)
			buf.append(" ").append(Utility.toHexString(bytes));
		return buf.toString();
	}

	public Attribute copy(ConstantPool _constant_pool) {
		Synthetic c = (Synthetic) clone();
		if (bytes != null) {
			c.bytes = new byte[bytes.length];
			System.arraycopy(bytes, 0, c.bytes, 0, bytes.length);
		}
		c.constant_pool = _constant_pool;
		return c;
	}
}
