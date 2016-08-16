package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeVisibleParameterAnnotations extends ParameterAnnotations {
	private static final long serialVersionUID = -4266572854750267070L;

	RuntimeVisibleParameterAnnotations(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
			throws IOException {
		super((byte) 14, name_index, length, file, constant_pool);
	}

	public Attribute copy(ConstantPool constant_pool) {
		Annotations c = (Annotations) clone();
		return c;
	}
}
