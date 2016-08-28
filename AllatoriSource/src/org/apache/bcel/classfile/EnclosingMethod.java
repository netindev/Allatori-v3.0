package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnclosingMethod extends Attribute {
	private static final long serialVersionUID = 9136852385761725494L;
	private int classIndex;
	private int methodIndex;

	public EnclosingMethod(int nameIndex, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
		this(nameIndex, len, dis.readUnsignedShort(), dis.readUnsignedShort(), cpool);
	}

	private EnclosingMethod(int nameIndex, int len, int classIdx, int methodIdx, ConstantPool cpool) {
		super((byte) 18, nameIndex, len, cpool);
		classIndex = classIdx;
		methodIndex = methodIdx;
	}

	@Override
	public void accept(Visitor v) {
		v.visitEnclosingMethod(this);
	}

	@Override
	public Attribute copy(ConstantPool constant_pool) {
		throw new RuntimeException("Not implemented yet!");
	}

	public final int getEnclosingClassIndex() {
		return classIndex;
	}

	public final int getEnclosingMethodIndex() {
		return methodIndex;
	}

	public final void setEnclosingClassIndex(int idx) {
		classIndex = idx;
	}

	public final void setEnclosingMethodIndex(int idx) {
		methodIndex = idx;
	}

	public final ConstantClass getEnclosingClass() {
		final ConstantClass c = (ConstantClass) constant_pool.getConstant(classIndex, (byte) 7);
		return c;
	}

	public final ConstantNameAndType getEnclosingMethod() {
		if (methodIndex == 0)
			return null;
		final ConstantNameAndType nat = ((ConstantNameAndType) constant_pool.getConstant(methodIndex, (byte) 12));
		return nat;
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(classIndex);
		file.writeShort(methodIndex);
	}
}
