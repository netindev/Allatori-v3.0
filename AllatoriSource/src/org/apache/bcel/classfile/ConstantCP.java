package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ConstantCP extends Constant {
	private static final long serialVersionUID = -6275762995206209402L;
	protected int class_index;
	protected int name_and_type_index;

	public ConstantCP(ConstantCP c) {
		this(c.getTag(), c.getClassIndex(), c.getNameAndTypeIndex());
	}

	ConstantCP(byte tag, DataInput file) throws IOException {
		this(tag, file.readUnsignedShort(), file.readUnsignedShort());
	}

	protected ConstantCP(byte tag, int class_index, int name_and_type_index) {
		super(tag);
		this.class_index = class_index;
		this.name_and_type_index = name_and_type_index;
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeShort(class_index);
		file.writeShort(name_and_type_index);
	}

	public final int getClassIndex() {
		return class_index;
	}

	public final int getNameAndTypeIndex() {
		return name_and_type_index;
	}

	public final void setClassIndex(int class_index) {
		this.class_index = class_index;
	}

	public String getClass(ConstantPool cp) {
		return cp.constantToString(class_index, (byte) 7);
	}

	public final void setNameAndTypeIndex(int name_and_type_index) {
		this.name_and_type_index = name_and_type_index;
	}

	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(class_index = ").append(class_index)
				.append(", name_and_type_index = ").append(name_and_type_index).append(")").toString();
	}
}
