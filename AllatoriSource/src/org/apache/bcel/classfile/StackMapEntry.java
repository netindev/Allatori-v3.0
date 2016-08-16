package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class StackMapEntry implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private int byte_code_offset;
	private int number_of_locals;
	private StackMapType[] types_of_locals;
	private int number_of_stack_items;
	private StackMapType[] types_of_stack_items;
	private ConstantPool constant_pool;

	StackMapEntry(DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(file.readShort(), file.readShort(), null, -1, null, constant_pool);
		types_of_locals = new StackMapType[number_of_locals];
		for (int i = 0; i < number_of_locals; i++)
			types_of_locals[i] = new StackMapType(file, constant_pool);
		number_of_stack_items = file.readShort();
		types_of_stack_items = new StackMapType[number_of_stack_items];
		for (int i = 0; i < number_of_stack_items; i++)
			types_of_stack_items[i] = new StackMapType(file, constant_pool);
	}

	public StackMapEntry(int byte_code_offset, int number_of_locals, StackMapType[] types_of_locals,
			int number_of_stack_items, StackMapType[] types_of_stack_items, ConstantPool constant_pool) {
		this.byte_code_offset = byte_code_offset;
		this.number_of_locals = number_of_locals;
		this.types_of_locals = types_of_locals;
		this.number_of_stack_items = number_of_stack_items;
		this.types_of_stack_items = types_of_stack_items;
		this.constant_pool = constant_pool;
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.writeShort(byte_code_offset);
		file.writeShort(number_of_locals);
		for (int i = 0; i < number_of_locals; i++)
			types_of_locals[i].dump(file);
		file.writeShort(number_of_stack_items);
		for (int i = 0; i < number_of_stack_items; i++)
			types_of_stack_items[i].dump(file);
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("(offset=").append(byte_code_offset);
		if (number_of_locals > 0) {
			buf.append(", locals={");
			for (int i = 0; i < number_of_locals; i++) {
				buf.append(types_of_locals[i]);
				if (i < number_of_locals - 1)
					buf.append(", ");
			}
			buf.append("}");
		}
		if (number_of_stack_items > 0) {
			buf.append(", stack items={");
			for (int i = 0; i < number_of_stack_items; i++) {
				buf.append(types_of_stack_items[i]);
				if (i < number_of_stack_items - 1)
					buf.append(", ");
			}
			buf.append("}");
		}
		buf.append(")");
		return buf.toString();
	}

	public void setByteCodeOffset(int b) {
		byte_code_offset = b;
	}

	public int getByteCodeOffset() {
		return byte_code_offset;
	}

	public void setNumberOfLocals(int n) {
		number_of_locals = n;
	}

	public int getNumberOfLocals() {
		return number_of_locals;
	}

	public void setTypesOfLocals(StackMapType[] t) {
		types_of_locals = t;
	}

	public StackMapType[] getTypesOfLocals() {
		return types_of_locals;
	}

	public void setNumberOfStackItems(int n) {
		number_of_stack_items = n;
	}

	public int getNumberOfStackItems() {
		return number_of_stack_items;
	}

	public void setTypesOfStackItems(StackMapType[] t) {
		types_of_stack_items = t;
	}

	public StackMapType[] getTypesOfStackItems() {
		return types_of_stack_items;
	}

	public StackMapEntry copy() {
		StackMapEntry stackmapentry;
		try {
			stackmapentry = (StackMapEntry) this.clone();
		} catch (CloneNotSupportedException clonenotsupportedexception) {
			return null;
		}
		return stackmapentry;
	}

	public void accept(Visitor v) {
		v.visitStackMapEntry(this);
	}

	public final ConstantPool getConstantPool() {
		return constant_pool;
	}

	public final void setConstantPool(ConstantPool constant_pool) {
		this.constant_pool = constant_pool;
	}
}
