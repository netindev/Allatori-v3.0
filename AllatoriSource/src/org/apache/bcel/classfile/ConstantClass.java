/* ConstantClass - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantClass extends Constant implements ConstantObject {
	private static final long serialVersionUID = -1083450233715258720L;
	private int name_index;

	public ConstantClass(ConstantClass c) {
		this(c.getNameIndex());
	}

	ConstantClass(DataInput file) throws IOException {
		this(file.readUnsignedShort());
	}

	public ConstantClass(int name_index) {
		super((byte) 7);
		this.name_index = name_index;
	}

	@Override
	public void accept(Visitor v) {
		v.visitConstantClass(this);
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeShort(name_index);
	}

	public final int getNameIndex() {
		return name_index;
	}

	public final void setNameIndex(int name_index) {
		this.name_index = name_index;
	}

	@Override
	public Object getConstantValue(ConstantPool cp) {
		final Constant c = cp.getConstant(name_index, (byte) 1);
		return ((ConstantUtf8) c).getBytes();
	}

	public String getBytes(ConstantPool cp) {
		return (String) getConstantValue(cp);
	}

	@Override
	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(name_index = ").append(name_index).append(")")
				.toString();
	}
}
