package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class EnumElementValue extends ElementValue {
	private int typeIdx;
	private int valueIdx;

	public EnumElementValue(int type, int typeIdx, int valueIdx, ConstantPool cpool) {
		super(type, cpool);
		if (type != 101)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type enum can be built with this ctor - type specified: ")
					.append(type).toString());
		this.typeIdx = typeIdx;
		this.valueIdx = valueIdx;
	}

	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		dos.writeShort(typeIdx);
		dos.writeShort(valueIdx);
	}

	public String stringifyValue() {
		ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(valueIdx, (byte) 1);
		return cu8.getBytes();
	}

	public String getEnumTypeString() {
		ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(typeIdx, (byte) 1);
		return cu8.getBytes();
	}

	public String getEnumValueString() {
		ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(valueIdx, (byte) 1);
		return cu8.getBytes();
	}

	public int getValueIndex() {
		return valueIdx;
	}

	public int getTypeIndex() {
		return typeIdx;
	}
}
