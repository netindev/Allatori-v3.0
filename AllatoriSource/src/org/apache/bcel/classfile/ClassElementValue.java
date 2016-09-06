/* ClassElementValue - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClassElementValue extends ElementValue {
	private final int idx;

	public ClassElementValue(int type, int idx, ConstantPool cpool) {
		super(type, cpool);
		this.idx = idx;
	}

	public int getIndex() {
		return idx;
	}

	public String getClassString() {
		final ConstantUtf8 c = (ConstantUtf8) cpool.getConstant(idx, (byte) 1);
		return c.getBytes();
	}

	@Override
	public String stringifyValue() {
		final ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(idx, (byte) 1);
		return cu8.getBytes();
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		dos.writeShort(idx);
	}
}
