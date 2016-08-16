package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantLong extends Constant implements ConstantObject {
	private static final long serialVersionUID = 8495971186433816161L;
	private long bytes;

	public ConstantLong(long bytes) {
		super((byte) 5);
		this.bytes = bytes;
	}

	public ConstantLong(ConstantLong c) {
		this(c.getBytes());
	}

	ConstantLong(DataInput file) throws IOException {
		this(file.readLong());
	}

	public void accept(Visitor v) {
		v.visitConstantLong(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeLong(bytes);
	}

	public final long getBytes() {
		return bytes;
	}

	public final void setBytes(long bytes) {
		this.bytes = bytes;
	}

	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(bytes = ").append(bytes).append(")").toString();
	}

	public Object getConstantValue(ConstantPool cp) {
		return Long.valueOf(bytes);
	}
}
