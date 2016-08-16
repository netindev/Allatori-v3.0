package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ExceptionTable extends Attribute {
	private static final long serialVersionUID = 2045358830660883220L;
	private int number_of_exceptions;
	private int[] exception_index_table;

	public ExceptionTable(ExceptionTable c) {
		this(c.getNameIndex(), c.getLength(), c.getExceptionIndexTable(), c.getConstantPool());
	}

	public ExceptionTable(int name_index, int length, int[] exception_index_table, ConstantPool constant_pool) {
		super((byte) 3, name_index, length, constant_pool);
		setExceptionIndexTable(exception_index_table);
	}

	ExceptionTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, (int[]) null, constant_pool);
		number_of_exceptions = file.readUnsignedShort();
		exception_index_table = new int[number_of_exceptions];
		for (int i = 0; i < number_of_exceptions; i++)
			exception_index_table[i] = file.readUnsignedShort();
	}

	public void accept(Visitor v) {
		v.visitExceptionTable(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(number_of_exceptions);
		for (int i = 0; i < number_of_exceptions; i++)
			file.writeShort(exception_index_table[i]);
	}

	public final int[] getExceptionIndexTable() {
		return exception_index_table;
	}

	public final int getNumberOfExceptions() {
		return number_of_exceptions;
	}

	public final String[] getExceptionNames() {
		String[] names = new String[number_of_exceptions];
		for (int i = 0; i < number_of_exceptions; i++)
			names[i] = constant_pool.getConstantString(exception_index_table[i], (byte) 7).replace('/', '.');
		return names;
	}

	public final void setExceptionIndexTable(int[] exception_index_table) {
		this.exception_index_table = exception_index_table;
		number_of_exceptions = exception_index_table == null ? 0 : exception_index_table.length;
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < number_of_exceptions; i++) {
			String str = constant_pool.getConstantString(exception_index_table[i], (byte) 7);
			buf.append(Utility.compactClassName(str, false));
			if (i < number_of_exceptions - 1)
				buf.append(", ");
		}
		return buf.toString();
	}

	public Attribute copy(ConstantPool _constant_pool) {
		ExceptionTable c = (ExceptionTable) clone();
		if (exception_index_table != null) {
			c.exception_index_table = new int[exception_index_table.length];
			System.arraycopy(exception_index_table, 0, c.exception_index_table, 0, exception_index_table.length);
		}
		c.constant_pool = _constant_pool;
		return c;
	}
}
