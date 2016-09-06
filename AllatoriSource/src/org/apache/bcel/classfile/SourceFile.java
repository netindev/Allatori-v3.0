/* SourceFile - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class SourceFile extends Attribute {
	private static final long serialVersionUID = -804226255663222912L;
	private int sourcefile_index;

	public SourceFile(SourceFile c) {
		this(c.getNameIndex(), c.getLength(), c.getSourceFileIndex(), c.getConstantPool());
	}

	SourceFile(int name_index, int length, DataInput file, ConstantPool constant_pool) throws IOException {
		this(name_index, length, file.readUnsignedShort(), constant_pool);
	}

	public SourceFile(int name_index, int length, int sourcefile_index, ConstantPool constant_pool) {
		super((byte) 0, name_index, length, constant_pool);
		this.sourcefile_index = sourcefile_index;
	}

	@Override
	public void accept(Visitor v) {
		v.visitSourceFile(this);
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		super.dump(file);
		file.writeShort(sourcefile_index);
	}

	public final int getSourceFileIndex() {
		return sourcefile_index;
	}

	public final void setSourceFileIndex(int sourcefile_index) {
		this.sourcefile_index = sourcefile_index;
	}

	public final String getSourceFileName() {
		final ConstantUtf8 c = ((ConstantUtf8) constant_pool.getConstant(sourcefile_index, (byte) 1));
		return c.getBytes();
	}

	@Override
	public final String toString() {
		return new StringBuilder().append("SourceFile(").append(getSourceFileName()).append(")").toString();
	}

	@Override
	public Attribute copy(ConstantPool _constant_pool) {
		return (SourceFile) clone();
	}
}
