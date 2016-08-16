package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTable extends Attribute {
	private static final long serialVersionUID = 6780929007774637689L;
	private int local_variable_table_length;
	private LocalVariable[] local_variable_table;

	public LocalVariableTable(LocalVariableTable c) {
		this(c.getNameIndex(), c.getLength(), c.getLocalVariableTable(), c.getConstantPool());
	}

	public LocalVariableTable(int name_index, int length, LocalVariable[] local_variable_table,
			ConstantPool constant_pool) {
		super((byte) 5, name_index, length, constant_pool);
		setLocalVariableTable(local_variable_table);
	}

	LocalVariableTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
			throws IOException {
		this(name_index, length, (LocalVariable[]) null, constant_pool);
		local_variable_table_length = file.readUnsignedShort();
		local_variable_table = new LocalVariable[local_variable_table_length];
		for (int i = 0; i < local_variable_table_length; i++)
			local_variable_table[i] = new LocalVariable(file, constant_pool);
	}

	public void accept(Visitor v) {
		v.visitLocalVariableTable(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(local_variable_table_length);
		for (int i = 0; i < local_variable_table_length; i++)
			local_variable_table[i].dump(file);
	}

	public final LocalVariable[] getLocalVariableTable() {
		return local_variable_table;
	}

	/**
	 * @deprecated
	 */
	public final LocalVariable getLocalVariable(int index) {
		for (int i = 0; i < local_variable_table_length; i++) {
			if (local_variable_table[i].getIndex() == index)
				return local_variable_table[i];
		}
		return null;
	}

	public final LocalVariable getLocalVariable(int index, int pc) {
		for (int i = 0; i < local_variable_table_length; i++) {
			if (local_variable_table[i].getIndex() == index) {
				int start_pc = local_variable_table[i].getStartPC();
				int end_pc = start_pc + local_variable_table[i].getLength();
				if (pc >= start_pc && pc <= end_pc)
					return local_variable_table[i];
			}
		}
		return null;
	}

	public final void setLocalVariableTable(LocalVariable[] local_variable_table) {
		this.local_variable_table = local_variable_table;
		local_variable_table_length = local_variable_table == null ? 0 : local_variable_table.length;
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < local_variable_table_length; i++) {
			buf.append(local_variable_table[i].toString());
			if (i < local_variable_table_length - 1)
				buf.append('\n');
		}
		return buf.toString();
	}

	public Attribute copy(ConstantPool _constant_pool) {
		LocalVariableTable c = (LocalVariableTable) clone();
		c.local_variable_table = new LocalVariable[local_variable_table_length];
		for (int i = 0; i < local_variable_table_length; i++)
			c.local_variable_table[i] = local_variable_table[i].copy();
		c.constant_pool = _constant_pool;
		return c;
	}

	public final int getTableLength() {
		return local_variable_table_length;
	}
}
