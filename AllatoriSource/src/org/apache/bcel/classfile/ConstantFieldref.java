package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantFieldref extends ConstantCP {
	private static final long serialVersionUID = -3993784840787819802L;

	public ConstantFieldref(ConstantFieldref c) {
		super((byte) 9, c.getClassIndex(), c.getNameAndTypeIndex());
	}

	ConstantFieldref(DataInputStream file) throws IOException {
		super((byte) 9, file);
	}

	public ConstantFieldref(int class_index, int name_and_type_index) {
		super((byte) 9, class_index, name_and_type_index);
	}

	public void accept(Visitor v) {
		v.visitConstantFieldref(this);
	}
}
