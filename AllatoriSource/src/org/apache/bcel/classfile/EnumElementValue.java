package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class EnumElementValue extends ElementValue {
	private final int typeIdx;
	private final int valueIdx;

	public EnumElementValue(int type, int typeIdx, int valueIdx, ConstantPool cpool) {
		super(type, cpool);
		if (type != 101)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type enum can be built with this ctor - type specified: ")
					.append(type).toString());
		this.typeIdx = typeIdx;
		this.valueIdx = valueIdx;
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		dos.writeShort(typeIdx);
		dos.writeShort(valueIdx);
	}

	@Override
	public String stringifyValue() {
		final ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(valueIdx, (byte) 1);
		return cu8.getBytes();
	}

	public String getEnumTypeString() {
		final ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(typeIdx, (byte) 1);
		return cu8.getBytes();
	}

	public String getEnumValueString() {
		final ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(valueIdx, (byte) 1);
		return cu8.getBytes();
	}

	public int getValueIndex() {
		return valueIdx;
	}

	public int getTypeIndex() {
		return typeIdx;
	}
}
