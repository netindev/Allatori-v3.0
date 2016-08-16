package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.ElementValuePair;

public class ElementValuePairGen {
	public void setNameIndex(int nameIdx) {
		this.nameIdx = nameIdx;
	}

	private int nameIdx;
	private ElementValueGen value;
	private ConstantPoolGen cpool;

	public ElementValuePairGen(ElementValuePair nvp, ConstantPoolGen cpool, boolean copyPoolEntries) {
		this.cpool = cpool;
		if (copyPoolEntries)
			nameIdx = cpool.addUtf8(nvp.getNameString());
		else
			nameIdx = nvp.getNameIndex();
		value = ElementValueGen.copy(nvp.getValue(), cpool, copyPoolEntries);
	}

	public ElementValuePair getElementNameValuePair() {
		ElementValue immutableValue = value.getElementValue();
		return new ElementValuePair(nameIdx, immutableValue, cpool.getConstantPool());
	}

	protected ElementValuePairGen(int idx, ElementValueGen value, ConstantPoolGen cpool) {
		nameIdx = idx;
		this.value = value;
		this.cpool = cpool;
	}

	public ElementValuePairGen(String name, ElementValueGen value, ConstantPoolGen cpool) {
		nameIdx = cpool.addUtf8(name);
		this.value = value;
		this.cpool = cpool;
	}

	protected void dump(DataOutputStream dos) throws IOException {
		dos.writeShort(nameIdx);
		value.dump(dos);
	}

	public int getNameIndex() {
		return nameIdx;
	}

	public final String getNameString() {
		return ((ConstantUtf8) cpool.getConstant(nameIdx)).getBytes();
	}

	public final ElementValueGen getValue() {
		return value;
	}

	public String toString() {
		return new StringBuilder().append("ElementValuePair:[").append(getNameString()).append("=")
				.append(value.stringifyValue()).append("]").toString();
	}
}
