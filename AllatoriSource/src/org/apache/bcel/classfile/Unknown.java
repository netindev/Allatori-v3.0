package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Unknown extends Attribute {
	private static final long serialVersionUID = -4099655108069755015L;
	private byte[] bytes;
	private final String name;
	private static final Map unknown_attributes = new HashMap();

	static Unknown[] getUnknownAttributes() {
		final Unknown[] unknowns = new Unknown[unknown_attributes.size()];
		unknown_attributes.values().toArray(unknowns);
		unknown_attributes.clear();
		return unknowns;
	}

	public Unknown(Unknown c) {
		this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
	}

	public Unknown(int name_index, int length, byte[] bytes, ConstantPool constant_pool) {
		super((byte) -1, name_index, length, constant_pool);
		this.bytes = bytes;
		name = ((ConstantUtf8) constant_pool.getConstant(name_index, (byte) 1)).getBytes();
		unknown_attributes.put(name, this);
	}

	Unknown(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, (byte[]) null, constant_pool);
		if (length > 0) {
			bytes = new byte[length];
			file.readFully(bytes);
		}
	}

	@Override
	public void accept(Visitor v) {
		v.visitUnknown(this);
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

	@Override
	public final String getName() {
		return name;
	}

	public final void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public final String toString() {
		if (length == 0 || bytes == null)
			return new StringBuilder().append("(Unknown attribute ").append(name).append(")").toString();
		String hex;
		if (length > 10) {
			final byte[] tmp = new byte[10];
			System.arraycopy(bytes, 0, tmp, 0, 10);
			hex = new StringBuilder().append(Utility.toHexString(tmp)).append("... (truncated)").toString();
		} else
			hex = Utility.toHexString(bytes);
		return new StringBuilder().append("(Unknown attribute ").append(name).append(": ").append(hex).append(")")
				.toString();
	}

	@Override
	public Attribute copy(ConstantPool _constant_pool) {
		final Unknown c = (Unknown) clone();
		if (bytes != null) {
			c.bytes = new byte[bytes.length];
			System.arraycopy(bytes, 0, c.bytes, 0, bytes.length);
		}
		c.constant_pool = _constant_pool;
		return c;
	}
}
