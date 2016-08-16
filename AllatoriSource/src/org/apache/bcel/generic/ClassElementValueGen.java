package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassElementValue;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValue;

public class ClassElementValueGen extends ElementValueGen {
	public void setIndex(int idx) {
		this.idx = idx;
	}

	private int idx;

	protected ClassElementValueGen(int typeIdx, ConstantPoolGen cpool) {
		super(99, cpool);
		idx = typeIdx;
	}

	public ClassElementValueGen(ObjectType t, ConstantPoolGen cpool) {
		super(99, cpool);
		idx = cpool.addUtf8(t.getSignature());
	}

	public ElementValue getElementValue() {
		return new ClassElementValue(type, idx, cpGen.getConstantPool());
	}

	public ClassElementValueGen(ClassElementValue value, ConstantPoolGen cpool, boolean copyPoolEntries) {
		super(99, cpool);
		if (copyPoolEntries)
			idx = cpool.addUtf8(value.getClassString());
		else
			idx = value.getIndex();
	}

	public int getIndex() {
		return idx;
	}

	public String getClassString() {
		ConstantUtf8 cu8 = (ConstantUtf8) getConstantPool().getConstant(idx);
		return cu8.getBytes();
	}

	public String stringifyValue() {
		return getClassString();
	}

	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		dos.writeShort(idx);
	}
}
