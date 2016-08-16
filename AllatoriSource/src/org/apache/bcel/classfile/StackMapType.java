package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.bcel.Constants;

public final class StackMapType implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private byte type;
	private int index = -1;
	private ConstantPool constant_pool;

	StackMapType(DataInput file, ConstantPool constant_pool) throws IOException {
		this(file.readByte(), -1, constant_pool);
		if (hasIndex())
			setIndex(file.readShort());
		setConstantPool(constant_pool);
	}

	public StackMapType(byte type, int index, ConstantPool constant_pool) {
		setType(type);
		setIndex(index);
		setConstantPool(constant_pool);
	}

	public void setType(byte t) {
		if (t < 0 || t > 8)
			throw new RuntimeException(
					new StringBuilder().append("Illegal type for StackMapType: ").append(t).toString());
		type = t;
	}

	public byte getType() {
		return type;
	}

	public void setIndex(int t) {
		index = t;
	}

	public int getIndex() {
		return index;
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(type);
		if (hasIndex())
			file.writeShort(getIndex());
	}

	public final boolean hasIndex() {
		return type == 7 || type == 8;
	}

	private String printIndex() {
		if (type == 7) {
			if (index < 0)
				return ", class=<unknown>";
			return new StringBuilder().append(", class=").append(constant_pool.constantToString(index, (byte) 7))
					.toString();
		}
		if (type == 8)
			return new StringBuilder().append(", offset=").append(index).toString();
		return "";
	}

	public final String toString() {
		return new StringBuilder().append("(type=").append(Constants.ITEM_NAMES[type]).append(printIndex()).append(")")
				.toString();
	}

	public StackMapType copy() {
		StackMapType stackmaptype;
		try {
			stackmaptype = (StackMapType) this.clone();
		} catch (CloneNotSupportedException clonenotsupportedexception) {
			return null;
		}
		return stackmaptype;
	}

	public final ConstantPool getConstantPool() {
		return constant_pool;
	}

	public final void setConstantPool(ConstantPool constant_pool) {
		this.constant_pool = constant_pool;
	}
}
