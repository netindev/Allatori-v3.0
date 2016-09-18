package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ParameterAnnotations extends Attribute {
	private static final long serialVersionUID = 5234607357644462705L;
	private int num_parameters;
	private ParameterAnnotationEntry[] parameter_annotation_table;

	ParameterAnnotations(byte parameter_annotation_type, int name_index, int length, DataInputStream file,
			ConstantPool constant_pool) throws IOException {
		this(parameter_annotation_type, name_index, length, (ParameterAnnotationEntry[]) null, constant_pool);
		num_parameters = file.readUnsignedByte();
		parameter_annotation_table = new ParameterAnnotationEntry[num_parameters];
		for (int i = 0; i < num_parameters; i++)
			parameter_annotation_table[i] = new ParameterAnnotationEntry(file, constant_pool);
	}

	public ParameterAnnotations(byte parameter_annotation_type, int name_index, int length,
			ParameterAnnotationEntry[] parameter_annotation_table, ConstantPool constant_pool) {
		super(parameter_annotation_type, name_index, length, constant_pool);
		setParameterAnnotationTable(parameter_annotation_table);
	}

	@Override
	public void accept(Visitor v) {
		/* empty */
	}

	public final void setParameterAnnotationTable(ParameterAnnotationEntry[] parameter_annotation_table) {
		this.parameter_annotation_table = parameter_annotation_table;
		num_parameters = (parameter_annotation_table == null ? 0 : parameter_annotation_table.length);
	}

	public final ParameterAnnotationEntry[] getParameterAnnotationTable() {
		return parameter_annotation_table;
	}

	public ParameterAnnotationEntry[] getParameterAnnotationEntries() {
		return parameter_annotation_table;
	}

	public final int getNumParameterAnnotation() {
		return num_parameters;
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		super.dump(dos);
		dos.writeByte(parameter_annotation_table.length);
		for (int i = 0; i < parameter_annotation_table.length; i++)
			parameter_annotation_table[i].dump(dos);
	}
}
