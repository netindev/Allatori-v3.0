package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class ArrayElementValue extends ElementValue {
	private final ElementValue[] evalues;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < evalues.length; i++) {
			sb.append(evalues[i].toString());
			if (i + 1 < evalues.length)
				sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}

	public ArrayElementValue(int type, ElementValue[] datums, ConstantPool cpool) {
		super(type, cpool);
		if (type != 91)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type array can be built with this ctor - type specified: ")
					.append(type).toString());
		evalues = datums;
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		dos.writeShort(evalues.length);
		for (int i = 0; i < evalues.length; i++)
			evalues[i].dump(dos);
	}

	@Override
	public String stringifyValue() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < evalues.length; i++) {
			sb.append(evalues[i].stringifyValue());
			if (i + 1 < evalues.length)
				sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	public ElementValue[] getElementValuesArray() {
		return evalues;
	}

	public int getElementValuesArraySize() {
		return evalues.length;
	}
}
