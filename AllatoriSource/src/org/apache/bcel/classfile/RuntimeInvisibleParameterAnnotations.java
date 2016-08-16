package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisibleParameterAnnotations extends ParameterAnnotations {
	private static final long serialVersionUID = 270153155050617200L;

	RuntimeInvisibleParameterAnnotations(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
			throws IOException {
		super((byte) 15, name_index, length, file, constant_pool);
	}

	public Attribute copy(ConstantPool constant_pool) {
		Annotations c = (Annotations) clone();
		return c;
	}
}
