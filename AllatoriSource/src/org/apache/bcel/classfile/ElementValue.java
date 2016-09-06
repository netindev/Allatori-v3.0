/* ElementValue - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ElementValue {
	protected int type;
	protected ConstantPool cpool;
	public static final int STRING = 115;
	public static final int ENUM_CONSTANT = 101;
	public static final int CLASS = 99;
	public static final int ANNOTATION = 64;
	public static final int ARRAY = 91;
	public static final int PRIMITIVE_INT = 73;
	public static final int PRIMITIVE_BYTE = 66;
	public static final int PRIMITIVE_CHAR = 67;
	public static final int PRIMITIVE_DOUBLE = 68;
	public static final int PRIMITIVE_FLOAT = 70;
	public static final int PRIMITIVE_LONG = 74;
	public static final int PRIMITIVE_SHORT = 83;
	public static final int PRIMITIVE_BOOLEAN = 90;

	@Override
	public String toString() {
		return stringifyValue();
	}

	protected ElementValue(int type, ConstantPool cpool) {
		this.type = type;
		this.cpool = cpool;
	}

	public int getElementValueType() {
		return type;
	}

	public abstract String stringifyValue();

	public abstract void dump(DataOutputStream dataoutputstream) throws IOException;

	public static ElementValue readElementValue(DataInputStream dis, ConstantPool cpool) throws IOException {
		final byte type = dis.readByte();
		switch (type) {
		case 66:
			return new SimpleElementValue(66, dis.readUnsignedShort(), cpool);
		case 67:
			return new SimpleElementValue(67, dis.readUnsignedShort(), cpool);
		case 68:
			return new SimpleElementValue(68, dis.readUnsignedShort(), cpool);
		case 70:
			return new SimpleElementValue(70, dis.readUnsignedShort(), cpool);
		case 73:
			return new SimpleElementValue(73, dis.readUnsignedShort(), cpool);
		case 74:
			return new SimpleElementValue(74, dis.readUnsignedShort(), cpool);
		case 83:
			return new SimpleElementValue(83, dis.readUnsignedShort(), cpool);
		case 90:
			return new SimpleElementValue(90, dis.readUnsignedShort(), cpool);
		case 115:
			return new SimpleElementValue(115, dis.readUnsignedShort(), cpool);
		case 101:
			return new EnumElementValue(101, dis.readUnsignedShort(), dis.readUnsignedShort(), cpool);
		case 99:
			return new ClassElementValue(99, dis.readUnsignedShort(), cpool);
		case 64:
			return new AnnotationElementValue(64, AnnotationEntry.read(dis, cpool, false), cpool);
		case 91: {
			final int numArrayVals = dis.readUnsignedShort();
			final ElementValue[] evalues = new ElementValue[numArrayVals];
			for (int j = 0; j < numArrayVals; j++)
				evalues[j] = readElementValue(dis, cpool);
			return new ArrayElementValue(91, evalues, cpool);
		}
		default:
			throw new RuntimeException(new StringBuilder().append("Unexpected element value kind in annotation: ")
					.append(type).toString());
		}
	}

	public String toShortString() {
		return stringifyValue();
	}
}
