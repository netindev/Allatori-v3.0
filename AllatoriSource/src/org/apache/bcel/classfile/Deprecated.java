package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.Constants;

public final class Deprecated extends Attribute {
	private static final long serialVersionUID = -2242528405240201000L;
	private byte[] bytes;

	public Deprecated(Deprecated c) {
		this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
	}

	public Deprecated(int name_index, int length, byte[] bytes, ConstantPool constant_pool) {
		super((byte) 8, name_index, length, constant_pool);
		this.bytes = bytes;
	}

	Deprecated(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, (byte[]) null, constant_pool);
		if (length > 0) {
			bytes = new byte[length];
			file.readFully(bytes);
			System.err.println("Deprecated attribute with length > 0");
		}
	}

	@Override
	public void accept(Visitor v) {
		v.visitDeprecated(this);
	}

	@Override
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

	@Override
	public final String toString() {
		return Constants.ATTRIBUTE_NAMES[8];
	}

	@Override
	public Attribute copy(ConstantPool _constant_pool) {
		final Deprecated c = (Deprecated) clone();
		if (bytes != null) {
			c.bytes = new byte[bytes.length];
			System.arraycopy(bytes, 0, c.bytes, 0, bytes.length);
		}
		c.constant_pool = _constant_pool;
		return c;
	}
}
