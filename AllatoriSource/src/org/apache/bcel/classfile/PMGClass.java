package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class PMGClass extends Attribute {
	private static final long serialVersionUID = -7075964153234211509L;
	private int pmg_class_index;
	private int pmg_index;

	public PMGClass(PMGClass c) {
		this(c.getNameIndex(), c.getLength(), c.getPMGIndex(), c.getPMGClassIndex(), c.getConstantPool());
	}

	PMGClass(int name_index, int length, DataInput file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, file.readUnsignedShort(), file.readUnsignedShort(), constant_pool);
	}

	public PMGClass(int name_index, int length, int pmg_index, int pmg_class_index, ConstantPool constant_pool) {
		super((byte) 9, name_index, length, constant_pool);
		this.pmg_index = pmg_index;
		this.pmg_class_index = pmg_class_index;
	}

	@Override
	public void accept(Visitor v) {
		System.err.println("Visiting non-standard PMGClass object");
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(pmg_index);
		file.writeShort(pmg_class_index);
	}

	public final int getPMGClassIndex() {
		return pmg_class_index;
	}

	public final void setPMGClassIndex(int pmg_class_index) {
		this.pmg_class_index = pmg_class_index;
	}

	public final int getPMGIndex() {
		return pmg_index;
	}

	public final void setPMGIndex(int pmg_index) {
		this.pmg_index = pmg_index;
	}

	public final String getPMGName() {
		final ConstantUtf8 c = (ConstantUtf8) constant_pool.getConstant(pmg_index, (byte) 1);
		return c.getBytes();
	}

	public final String getPMGClassName() {
		final ConstantUtf8 c = ((ConstantUtf8) constant_pool.getConstant(pmg_class_index, (byte) 1));
		return c.getBytes();
	}

	@Override
	public final String toString() {
		return new StringBuilder().append("PMGClass(").append(getPMGName()).append(", ").append(getPMGClassName())
				.append(")").toString();
	}

	@Override
	public Attribute copy(ConstantPool _constant_pool) {
		return (PMGClass) clone();
	}
}
