package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTypeTable extends Attribute {
	private static final long serialVersionUID = -1082157891095177114L;
	private int local_variable_type_table_length;
	private LocalVariable[] local_variable_type_table;

	public LocalVariableTypeTable(LocalVariableTypeTable c) {
		this(c.getNameIndex(), c.getLength(), c.getLocalVariableTypeTable(), c.getConstantPool());
	}

	public LocalVariableTypeTable(int name_index, int length, LocalVariable[] local_variable_table,
			ConstantPool constant_pool) {
		super((byte) 17, name_index, length, constant_pool);
		setLocalVariableTable(local_variable_table);
	}

	LocalVariableTypeTable(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
		this(nameIdx, len, (LocalVariable[]) null, cpool);
		local_variable_type_table_length = dis.readUnsignedShort();
		local_variable_type_table = new LocalVariable[local_variable_type_table_length];
		for (int i = 0; i < local_variable_type_table_length; i++)
			local_variable_type_table[i] = new LocalVariable(dis, cpool);
	}

	public void accept(Visitor v) {
		v.visitLocalVariableTypeTable(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(local_variable_type_table_length);
		for (int i = 0; i < local_variable_type_table_length; i++)
			local_variable_type_table[i].dump(file);
	}

	public final LocalVariable[] getLocalVariableTypeTable() {
		return local_variable_type_table;
	}

	public final LocalVariable getLocalVariable(int index) {
		for (int i = 0; i < local_variable_type_table_length; i++) {
			if (local_variable_type_table[i].getIndex() == index)
				return local_variable_type_table[i];
		}
		return null;
	}

	public final void setLocalVariableTable(LocalVariable[] local_variable_table) {
		local_variable_type_table = local_variable_table;
		local_variable_type_table_length = local_variable_table == null ? 0 : local_variable_table.length;
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < local_variable_type_table_length; i++) {
			buf.append(local_variable_type_table[i].toString());
			if (i < local_variable_type_table_length - 1)
				buf.append('\n');
		}
		return buf.toString();
	}

	public Attribute copy(ConstantPool constant_pool) {
		LocalVariableTypeTable c = (LocalVariableTypeTable) clone();
		c.local_variable_type_table = new LocalVariable[local_variable_type_table_length];
		for (int i = 0; i < local_variable_type_table_length; i++)
			c.local_variable_type_table[i] = local_variable_type_table[i].copy();
		c.constant_pool = constant_pool;
		return c;
	}

	public final int getTableLength() {
		return local_variable_type_table_length;
	}
}
