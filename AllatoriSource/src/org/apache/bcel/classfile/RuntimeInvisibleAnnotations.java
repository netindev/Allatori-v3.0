package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RuntimeInvisibleAnnotations extends Annotations {
	private static final long serialVersionUID = 5274986004117955967L;

	RuntimeInvisibleAnnotations(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
			throws IOException {
		super((byte) 13, name_index, length, file, constant_pool, false);
	}

	public Attribute copy(ConstantPool constant_pool) {
		Annotations c = (Annotations) clone();
		return c;
	}

	public final void dump(DataOutputStream dos) throws IOException {
		super.dump(dos);
		writeAnnotations(dos);
	}
}
