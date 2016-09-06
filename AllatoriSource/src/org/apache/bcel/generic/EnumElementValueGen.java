/* EnumElementValueGen - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.EnumElementValue;

public class EnumElementValueGen extends ElementValueGen {
	private int typeIdx;

	public void setValueIndex(int valueIdx) {
		this.valueIdx = valueIdx;
	}

	public void setTypeIndex(int typeIdx) {
		this.typeIdx = typeIdx;
	}

	private int valueIdx;

	protected EnumElementValueGen(int typeIdx, int valueIdx, ConstantPoolGen cpool) {
		super(101, cpool);
		if (type != 101)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type enum can be built with this ctor - type specified: ")
					.append(type).toString());
		this.typeIdx = typeIdx;
		this.valueIdx = valueIdx;
	}

	@Override
	public ElementValue getElementValue() {
		System.err.println(new StringBuilder().append("Duplicating value: ").append(getEnumTypeString()).append(":")
				.append(getEnumValueString()).toString());
		return new EnumElementValue(type, typeIdx, valueIdx, cpGen.getConstantPool());
	}

	public EnumElementValueGen(ObjectType t, String value, ConstantPoolGen cpool) {
		super(101, cpool);
		typeIdx = cpool.addUtf8(t.getSignature());
		valueIdx = cpool.addUtf8(value);
	}

	public EnumElementValueGen(EnumElementValue value, ConstantPoolGen cpool, boolean copyPoolEntries) {
		super(101, cpool);
		if (copyPoolEntries) {
			typeIdx = cpool.addUtf8(value.getEnumTypeString());
			valueIdx = cpool.addUtf8(value.getEnumValueString());
		} else {
			typeIdx = value.getTypeIndex();
			valueIdx = value.getValueIndex();
		}
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		dos.writeShort(typeIdx);
		dos.writeShort(valueIdx);
	}

	@Override
	public String stringifyValue() {
		final ConstantUtf8 cu8 = (ConstantUtf8) getConstantPool().getConstant(valueIdx);
		return cu8.getBytes();
	}

	public String getEnumTypeString() {
		return ((ConstantUtf8) getConstantPool().getConstant(typeIdx)).getBytes();
	}

	public String getEnumValueString() {
		return ((ConstantUtf8) getConstantPool().getConstant(valueIdx)).getBytes();
	}

	public int getValueIndex() {
		return valueIdx;
	}

	public int getTypeIndex() {
		return typeIdx;
	}
}
