/* ConstantString - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantString extends Constant implements ConstantObject {
	private static final long serialVersionUID = 6603144389219397225L;
	private int string_index;

	public ConstantString(ConstantString c) {
		this(c.getStringIndex());
	}

	ConstantString(DataInput file) throws IOException {
		this(file.readUnsignedShort());
	}

	public ConstantString(int string_index) {
		super((byte) 8);
		this.string_index = string_index;
	}

	@Override
	public void accept(Visitor v) {
		v.visitConstantString(this);
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeShort(string_index);
	}

	public final int getStringIndex() {
		return string_index;
	}

	public final void setStringIndex(int string_index) {
		this.string_index = string_index;
	}

	@Override
	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(string_index = ").append(string_index).append(")")
				.toString();
	}

	@Override
	public Object getConstantValue(ConstantPool cp) {
		final Constant c = cp.getConstant(string_index, (byte) 1);
		return ((ConstantUtf8) c).getBytes();
	}

	public String getBytes(ConstantPool cp) {
		return (String) getConstantValue(cp);
	}
}
