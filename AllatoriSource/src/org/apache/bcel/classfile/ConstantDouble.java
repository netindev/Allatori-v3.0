package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantDouble extends Constant implements ConstantObject {
	private static final long serialVersionUID = -7394764537394782136L;
	private double bytes;

	public ConstantDouble(double bytes) {
		super((byte) 6);
		this.bytes = bytes;
	}

	public ConstantDouble(ConstantDouble c) {
		this(c.getBytes());
	}

	ConstantDouble(DataInput file) throws IOException {
		this(file.readDouble());
	}

	@Override
	public void accept(Visitor v) {
		v.visitConstantDouble(this);
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeDouble(bytes);
	}

	public final double getBytes() {
		return bytes;
	}

	public final void setBytes(double bytes) {
		this.bytes = bytes;
	}

	@Override
	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(bytes = ").append(bytes).append(")").toString();
	}

	@Override
	public Object getConstantValue(ConstantPool cp) {
		return new Double(bytes);
	}
}
