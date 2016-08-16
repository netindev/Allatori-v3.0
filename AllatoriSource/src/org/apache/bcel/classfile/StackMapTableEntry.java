package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class StackMapTableEntry implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private int frame_type;
	private int byte_code_offset_delta;
	private int number_of_locals;
	private StackMapType[] types_of_locals;
	private int number_of_stack_items;
	private StackMapType[] types_of_stack_items;
	private ConstantPool constant_pool;

	StackMapTableEntry(DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(file.read(), -1, -1, null, -1, null, constant_pool);
		if (frame_type >= 0 && frame_type <= 63)
			byte_code_offset_delta = frame_type - 0;
		else if (frame_type >= 64 && frame_type <= 127) {
			byte_code_offset_delta = frame_type - 64;
			number_of_stack_items = 1;
			types_of_stack_items = new StackMapType[1];
			types_of_stack_items[0] = new StackMapType(file, constant_pool);
		} else if (frame_type == 247) {
			byte_code_offset_delta = file.readShort();
			number_of_stack_items = 1;
			types_of_stack_items = new StackMapType[1];
			types_of_stack_items[0] = new StackMapType(file, constant_pool);
		} else if (frame_type >= 248 && frame_type <= 250)
			byte_code_offset_delta = file.readShort();
		else if (frame_type == 251)
			byte_code_offset_delta = file.readShort();
		else if (frame_type >= 252 && frame_type <= 254) {
			byte_code_offset_delta = file.readShort();
			number_of_locals = frame_type - 251;
			types_of_locals = new StackMapType[number_of_locals];
			for (int i = 0; i < number_of_locals; i++)
				types_of_locals[i] = new StackMapType(file, constant_pool);
		} else if (frame_type == 255) {
			byte_code_offset_delta = file.readShort();
			number_of_locals = file.readShort();
			types_of_locals = new StackMapType[number_of_locals];
			for (int i = 0; i < number_of_locals; i++)
				types_of_locals[i] = new StackMapType(file, constant_pool);
			number_of_stack_items = file.readShort();
			types_of_stack_items = new StackMapType[number_of_stack_items];
			for (int i = 0; i < number_of_stack_items; i++)
				types_of_stack_items[i] = new StackMapType(file, constant_pool);
		} else
			throw new ClassFormatException(new StringBuilder()
					.append("Invalid frame type found while parsing stack map table: ").append(frame_type).toString());
	}

	public StackMapTableEntry(int tag, int byte_code_offset_delta, int number_of_locals, StackMapType[] types_of_locals,
			int number_of_stack_items, StackMapType[] types_of_stack_items, ConstantPool constant_pool) {
		frame_type = tag;
		this.byte_code_offset_delta = byte_code_offset_delta;
		this.number_of_locals = number_of_locals;
		this.types_of_locals = types_of_locals;
		this.number_of_stack_items = number_of_stack_items;
		this.types_of_stack_items = types_of_stack_items;
		this.constant_pool = constant_pool;
	}

	public final void dump(DataOutputStream file) throws IOException {
		file.write(frame_type);
		if (frame_type < 0 || frame_type > 63) {
			if (frame_type >= 64 && frame_type <= 127)
				types_of_stack_items[0].dump(file);
			else if (frame_type == 247) {
				file.writeShort(byte_code_offset_delta);
				types_of_stack_items[0].dump(file);
			} else if (frame_type >= 248 && frame_type <= 250)
				file.writeShort(byte_code_offset_delta);
			else if (frame_type == 251)
				file.writeShort(byte_code_offset_delta);
			else if (frame_type >= 252 && frame_type <= 254) {
				file.writeShort(byte_code_offset_delta);
				for (int i = 0; i < number_of_locals; i++)
					types_of_locals[i].dump(file);
			} else if (frame_type == 255) {
				file.writeShort(byte_code_offset_delta);
				file.writeShort(number_of_locals);
				for (int i = 0; i < number_of_locals; i++)
					types_of_locals[i].dump(file);
				file.writeShort(number_of_stack_items);
				for (int i = 0; i < number_of_stack_items; i++)
					types_of_stack_items[i].dump(file);
			} else
				throw new ClassFormatException(
						new StringBuilder().append("Invalid Stack map table tag: ").append(frame_type).toString());
		}
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("(");
		if (frame_type >= 0 && frame_type <= 63)
			buf.append("SAME");
		else if (frame_type >= 64 && frame_type <= 127)
			buf.append("SAME_LOCALS_1_STACK");
		else if (frame_type == 247)
			buf.append("SAME_LOCALS_1_STACK_EXTENDED");
		else if (frame_type >= 248 && frame_type <= 250)
			buf.append(new StringBuilder().append("CHOP ").append(251 - frame_type).toString());
		else if (frame_type == 251)
			buf.append("SAME_EXTENDED");
		else if (frame_type >= 252 && frame_type <= 254)
			buf.append(new StringBuilder().append("APPEND ").append(frame_type - 251).toString());
		else if (frame_type == 255)
			buf.append("FULL");
		else
			buf.append("UNKNOWN");
		buf.append(", offset delta=").append(byte_code_offset_delta);
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

	public void setByteCodeOffsetDelta(int b) {
		byte_code_offset_delta = b;
	}

	public int getByteCodeOffsetDelta() {
		return byte_code_offset_delta;
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

	public StackMapTableEntry copy() {
		StackMapTableEntry stackmaptableentry;
		try {
			stackmaptableentry = (StackMapTableEntry) this.clone();
		} catch (CloneNotSupportedException clonenotsupportedexception) {
			return null;
		}
		return stackmaptableentry;
	}

	public void accept(Visitor v) {
		v.visitStackMapTableEntry(this);
	}

	public final ConstantPool getConstantPool() {
		return constant_pool;
	}

	public final void setConstantPool(ConstantPool constant_pool) {
		this.constant_pool = constant_pool;
	}
}
