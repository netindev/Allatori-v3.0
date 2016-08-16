package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class LineNumberTable extends Attribute {
	private static final long serialVersionUID = -6967221519632128904L;
	private int line_number_table_length;
	private LineNumber[] line_number_table;

	public LineNumberTable(LineNumberTable c) {
		this(c.getNameIndex(), c.getLength(), c.getLineNumberTable(), c.getConstantPool());
	}

	public LineNumberTable(int name_index, int length, LineNumber[] line_number_table, ConstantPool constant_pool) {
		super((byte) 4, name_index, length, constant_pool);
		setLineNumberTable(line_number_table);
	}

	LineNumberTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, (LineNumber[]) null, constant_pool);
		line_number_table_length = file.readUnsignedShort();
		line_number_table = new LineNumber[line_number_table_length];
		for (int i = 0; i < line_number_table_length; i++)
			line_number_table[i] = new LineNumber(file);
	}

	public void accept(Visitor v) {
		v.visitLineNumberTable(this);
	}

	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(line_number_table_length);
		for (int i = 0; i < line_number_table_length; i++)
			line_number_table[i].dump(file);
	}

	public final LineNumber[] getLineNumberTable() {
		return line_number_table;
	}

	public final void setLineNumberTable(LineNumber[] line_number_table) {
		this.line_number_table = line_number_table;
		line_number_table_length = line_number_table == null ? 0 : line_number_table.length;
	}

	public final String toString() {
		StringBuilder buf = new StringBuilder();
		StringBuilder line = new StringBuilder();
		String newLine = System.getProperty("line.separator", "\n");
		for (int i = 0; i < line_number_table_length; i++) {
			line.append(line_number_table[i].toString());
			if (i < line_number_table_length - 1)
				line.append(", ");
			if (line.length() > 72) {
				line.append(newLine);
				buf.append(line.toString());
				line.setLength(0);
			}
		}
		buf.append((CharSequence) line);
		return buf.toString();
	}

	public int getSourceLine(int pos) {
		int l = 0;
		int r = line_number_table_length - 1;
		if (r < 0)
			return -1;
		int min_index = -1;
		int min = -1;
		do {
			int i = (l + r) / 2;
			int j = line_number_table[i].getStartPC();
			if (j == pos)
				return line_number_table[i].getLineNumber();
			if (pos < j)
				r = i - 1;
			else
				l = i + 1;
			if (j < pos && j > min) {
				min = j;
				min_index = i;
			}
		} while (l <= r);
		if (min_index < 0)
			return -1;
		return line_number_table[min_index].getLineNumber();
	}

	public Attribute copy(ConstantPool _constant_pool) {
		LineNumberTable c = (LineNumberTable) clone();
		c.line_number_table = new LineNumber[line_number_table_length];
		for (int i = 0; i < line_number_table_length; i++)
			c.line_number_table[i] = line_number_table[i].copy();
		c.constant_pool = _constant_pool;
		return c;
	}

	public final int getTableLength() {
		return line_number_table_length;
	}
}
