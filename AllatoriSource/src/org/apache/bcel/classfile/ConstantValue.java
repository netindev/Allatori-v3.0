/* ConstantValue - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantValue extends Attribute {
	private static final long serialVersionUID = -5668999920978520157L;
	private int constantvalue_index;

	public ConstantValue(ConstantValue c) {
		this(c.getNameIndex(), c.getLength(), c.getConstantValueIndex(), c.getConstantPool());
	}

	ConstantValue(int name_index, int length, DataInput file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, file.readUnsignedShort(), constant_pool);
	}

	public ConstantValue(int name_index, int length, int constantvalue_index, ConstantPool constant_pool) {
		super((byte) 1, name_index, length, constant_pool);
		this.constantvalue_index = constantvalue_index;
	}

	@Override
	public void accept(Visitor v) {
		v.visitConstantValue(this);
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(constantvalue_index);
	}

	public final int getConstantValueIndex() {
		return constantvalue_index;
	}

	public final void setConstantValueIndex(int constantvalue_index) {
		this.constantvalue_index = constantvalue_index;
	}

	@Override
	public final String toString() {
		Constant c = constant_pool.getConstant(constantvalue_index);
		String buf;
		switch (c.getTag()) {
		case 5:
			buf = String.valueOf(((ConstantLong) c).getBytes());
			break;
		case 4:
			buf = String.valueOf(((ConstantFloat) c).getBytes());
			break;
		case 6:
			buf = String.valueOf(((ConstantDouble) c).getBytes());
			break;
		case 3:
			buf = String.valueOf(((ConstantInteger) c).getBytes());
			break;
		case 8: {
			final int i = ((ConstantString) c).getStringIndex();
			c = constant_pool.getConstant(i, (byte) 1);
			buf = new StringBuilder().append("\"").append(Utility.convertString(((ConstantUtf8) c).getBytes()))
					.append("\"").toString();
			break;
		}
		default:
			throw new IllegalStateException(
					new StringBuilder().append("Type of ConstValue invalid: ").append(c).toString());
		}
		return buf;
	}

	@Override
	public Attribute copy(ConstantPool _constant_pool) {
		final ConstantValue c = (ConstantValue) clone();
		c.constant_pool = _constant_pool;
		return c;
	}
}
