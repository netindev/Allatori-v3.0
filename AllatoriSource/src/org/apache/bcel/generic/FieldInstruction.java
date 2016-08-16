package org.apache.bcel.generic;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ConstantPool;

public abstract class FieldInstruction extends FieldOrMethod {
	private static final long serialVersionUID = -7870956226459765817L;

	FieldInstruction() {
	}

	protected FieldInstruction(short opcode, int index) {
		super(opcode, index);
	}

	public String toString(ConstantPool cp) {
		return new StringBuilder().append(Constants.OPCODE_NAMES[opcode]).append(" ")
				.append(cp.constantToString(index, (byte) 9)).toString();
	}

	protected int getFieldSize(ConstantPoolGen cpg) {
		return Type.size(Type.getTypeSize(getSignature(cpg)));
	}

	public Type getType(ConstantPoolGen cpg) {
		return getFieldType(cpg);
	}

	public Type getFieldType(ConstantPoolGen cpg) {
		return Type.getType(getSignature(cpg));
	}

	public String getFieldName(ConstantPoolGen cpg) {
		return getName(cpg);
	}
}
