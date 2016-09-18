package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class InnerClass implements Cloneable, Node, Serializable {
	private static final long serialVersionUID = -7200195918166127614L;
	private int inner_class_index;
	private int outer_class_index;
	private int inner_name_index;
	private int inner_access_flags;

	public InnerClass(InnerClass c) {
		this(c.getInnerClassIndex(), c.getOuterClassIndex(), c.getInnerNameIndex(), c.getInnerAccessFlags());
	}

	InnerClass(DataInput file) throws IOException {
		this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort());
	}

	public InnerClass(int inner_class_index, int outer_class_index, int inner_name_index, int inner_access_flags) {
		this.inner_class_index = inner_class_index;
		this.outer_class_index = outer_class_index;
		this.inner_name_index = inner_name_index;
		this.inner_access_flags = inner_access_flags;
	}

	@Override
	public void accept(Visitor v) {
		v.visitInnerClass(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeShort(inner_class_index);
		file.writeShort(outer_class_index);
		file.writeShort(inner_name_index);
		file.writeShort(inner_access_flags);
	}

	public final int getInnerAccessFlags() {
		return inner_access_flags;
	}

	public final int getInnerClassIndex() {
		return inner_class_index;
	}

	public final int getInnerNameIndex() {
		return inner_name_index;
	}

	public final int getOuterClassIndex() {
		return outer_class_index;
	}

	public final void setInnerAccessFlags(int inner_access_flags) {
		this.inner_access_flags = inner_access_flags;
	}

	public final void setInnerClassIndex(int inner_class_index) {
		this.inner_class_index = inner_class_index;
	}

	public final void setInnerNameIndex(int inner_name_index) {
		this.inner_name_index = inner_name_index;
	}

	public final void setOuterClassIndex(int outer_class_index) {
		this.outer_class_index = outer_class_index;
	}

	@Override
	public final String toString() {
		return new StringBuilder().append("InnerClass(").append(inner_class_index).append(", ")
				.append(outer_class_index).append(", ").append(inner_name_index).append(", ").append(inner_access_flags)
				.append(")").toString();
	}

	public final String toString(ConstantPool constant_pool) {
		String inner_class_name = constant_pool.getConstantString(inner_class_index, (byte) 7);
		inner_class_name = Utility.compactClassName(inner_class_name);
		String outer_class_name;
		if (outer_class_index != 0) {
			outer_class_name = constant_pool.getConstantString(outer_class_index, (byte) 7);
			outer_class_name = Utility.compactClassName(outer_class_name);
		} else
			outer_class_name = "<not a member>";
		String inner_name;
		if (inner_name_index != 0)
			inner_name = ((ConstantUtf8) constant_pool.getConstant(inner_name_index, (byte) 1)).getBytes();
		else
			inner_name = "<anonymous>";
		String access = Utility.accessToString(inner_access_flags, true);
		access = (access.equals("") ? "" : new StringBuilder().append(access).append(" ").toString());
		return new StringBuilder().append("InnerClass:").append(access).append(inner_class_name).append("(\"")
				.append(outer_class_name).append("\", \"").append(inner_name).append("\")").toString();
	}

	public InnerClass copy() {
		InnerClass innerclass;
		try {
			innerclass = (InnerClass) this.clone();
		} catch (final CloneNotSupportedException clonenotsupportedexception) {
			return null;
		}
		return innerclass;
	}
}
