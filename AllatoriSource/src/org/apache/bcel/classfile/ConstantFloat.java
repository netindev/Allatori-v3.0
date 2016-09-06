/* ConstantFloat - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantFloat extends Constant implements ConstantObject {
	private static final long serialVersionUID = -2316732495687628398L;
	private float bytes;

	public ConstantFloat(float bytes) {
		super((byte) 4);
		this.bytes = bytes;
	}

	public ConstantFloat(ConstantFloat c) {
		this(c.getBytes());
	}

	ConstantFloat(DataInput file) throws IOException {
		this(file.readFloat());
	}

	@Override
	public void accept(Visitor v) {
		v.visitConstantFloat(this);
	}

	@Override
	public final void dump(DataOutputStream file) throws IOException {
		file.writeByte(tag);
		file.writeFloat(bytes);
	}

	public final float getBytes() {
		return bytes;
	}

	public final void setBytes(float bytes) {
		this.bytes = bytes;
	}

	@Override
	public final String toString() {
		return new StringBuilder().append(super.toString()).append("(bytes = ").append(bytes).append(")").toString();
	}

	@Override
	public Object getConstantValue(ConstantPool cp) {
		return new Float(bytes);
	}
}
