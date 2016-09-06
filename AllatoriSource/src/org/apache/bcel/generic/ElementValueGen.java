/* ElementValueGen - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ArrayElementValue;
import org.apache.bcel.classfile.ClassElementValue;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.EnumElementValue;
import org.apache.bcel.classfile.SimpleElementValue;

public abstract class ElementValueGen {
	protected int type;
	protected ConstantPoolGen cpGen;
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

	protected ElementValueGen(int type, ConstantPoolGen cpGen) {
		this.type = type;
		this.cpGen = cpGen;
	}

	public abstract ElementValue getElementValue();

	public int getElementValueType() {
		return type;
	}

	public abstract String stringifyValue();

	public abstract void dump(DataOutputStream dataoutputstream) throws IOException;

	public static ElementValueGen readElementValue(DataInputStream dis, ConstantPoolGen cpGen) throws IOException {
		final int type = dis.readUnsignedByte();
		switch (type) {
		case 66:
			return new SimpleElementValueGen(66, dis.readUnsignedShort(), cpGen);
		case 67:
			return new SimpleElementValueGen(67, dis.readUnsignedShort(), cpGen);
		case 68:
			return new SimpleElementValueGen(68, dis.readUnsignedShort(), cpGen);
		case 70:
			return new SimpleElementValueGen(70, dis.readUnsignedShort(), cpGen);
		case 73:
			return new SimpleElementValueGen(73, dis.readUnsignedShort(), cpGen);
		case 74:
			return new SimpleElementValueGen(74, dis.readUnsignedShort(), cpGen);
		case 83:
			return new SimpleElementValueGen(83, dis.readUnsignedShort(), cpGen);
		case 90:
			return new SimpleElementValueGen(90, dis.readUnsignedShort(), cpGen);
		case 115:
			return new SimpleElementValueGen(115, dis.readUnsignedShort(), cpGen);
		case 101:
			return new EnumElementValueGen(dis.readUnsignedShort(), dis.readUnsignedShort(), cpGen);
		case 99:
			return new ClassElementValueGen(dis.readUnsignedShort(), cpGen);
		case 64:
			return (new AnnotationElementValueGen(64,
					(new AnnotationEntryGen(AnnotationEntry.read(dis, cpGen.getConstantPool(), true), cpGen, false)),
					cpGen));
		case 91: {
			final int numArrayVals = dis.readUnsignedShort();
			final ElementValue[] evalues = new ElementValue[numArrayVals];
			for (int j = 0; j < numArrayVals; j++)
				evalues[j] = ElementValue.readElementValue(dis, cpGen.getConstantPool());
			return new ArrayElementValueGen(91, evalues, cpGen);
		}
		default:
			throw new RuntimeException(new StringBuilder().append("Unexpected element value kind in annotation: ")
					.append(type).toString());
		}
	}

	protected ConstantPoolGen getConstantPool() {
		return cpGen;
	}

	public static ElementValueGen copy(ElementValue value, ConstantPoolGen cpool, boolean copyPoolEntries) {
		switch (value.getElementValueType()) {
		case 66:
		case 67:
		case 68:
		case 70:
		case 73:
		case 74:
		case 83:
		case 90:
		case 115:
			return new SimpleElementValueGen((SimpleElementValue) value, cpool, copyPoolEntries);
		case 101:
			return new EnumElementValueGen((EnumElementValue) value, cpool, copyPoolEntries);
		case 64:
			return new AnnotationElementValueGen(((AnnotationElementValue) value), cpool, copyPoolEntries);
		case 91:
			return new ArrayElementValueGen((ArrayElementValue) value, cpool, copyPoolEntries);
		case 99:
			return new ClassElementValueGen((ClassElementValue) value, cpool, copyPoolEntries);
		default:
			throw new RuntimeException(new StringBuilder().append("Not implemented yet! (")
					.append(value.getElementValueType()).append(")").toString());
		}
	}
}
