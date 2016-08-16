package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantNameAndType extends Constant {
	private static final long serialVersionUID = -7913354727264034451L;
	private int name_index;
	private int signature_index;

	public ConstantNameAndType(ConstantNameAndType c) {
		this(c.getNameIndex(), c.getSignatureIndex());
	}

	ConstantNameAndType(DataInput file) throws IOException {
		this(file.readUnsignedShort(), file.readUnsignedShort());
	}

	public ConstantNameAndType(int name_index, int signature_index) {
		super((byte) 12);
		this.name_index = name_index;
		this.signature_index = signature_index;
	}

	public void accept(Visitor v) {
		v.visitConstantNameAndType(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeShort(name_index);
		file.writeShort(signature_index);
	}

	public final int getNameIndex() {
		return name_index;
	}

	public final String getName(ConstantPool cp) {
		return cp.constantToString(getNameIndex(), (byte) 1);
	}

	public final int getSignatureIndex() {
		return signature_index;
	}

	public final String getSignature(ConstantPool cp) {
		return cp.constantToString(getSignatureIndex(), (byte) 1);
	}

	public final void setNameIndex(int name_index) {
		this.name_index = name_index;
	}

	public final void setSignatureIndex(int signature_index) {
		this.signature_index = signature_index;
	}

	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(name_index = ").append(name_index)
				.append(", signature_index = ").append(signature_index).append(")").toString();
	}
}
