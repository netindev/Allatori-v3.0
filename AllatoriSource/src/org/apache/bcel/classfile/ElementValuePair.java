package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValuePair {
	private final ElementValue elementValue;
	private final ConstantPool constantPool;
	private final int elementNameIndex;

	public ElementValuePair(int elementNameIndex, ElementValue elementValue, ConstantPool constantPool) {
		this.elementValue = elementValue;
		this.elementNameIndex = elementNameIndex;
		this.constantPool = constantPool;
	}

	public String getNameString() {
		final ConstantUtf8 c = ((ConstantUtf8) constantPool.getConstant(elementNameIndex, (byte) 1));
		return c.getBytes();
	}

	public final ElementValue getValue() {
		return elementValue;
	}

	public int getNameIndex() {
		return elementNameIndex;
	}

	public String toShortString() {
		final StringBuilder result = new StringBuilder();
		result.append(getNameString()).append("=").append(getValue().toShortString());
		return result.toString();
	}

	protected void dump(DataOutputStream dos) throws IOException {
		dos.writeShort(elementNameIndex);
		elementValue.dump(dos);
	}
}
