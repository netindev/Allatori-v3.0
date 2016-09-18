package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.Constants;

public abstract class Attribute implements Cloneable, Node, Serializable {
	private static final long serialVersionUID = -1707826820310002955L;
	protected int name_index;
	protected int length;
	protected byte tag;
	protected ConstantPool constant_pool;
	private static final Map<String, AttributeReader> readers = new HashMap<String, AttributeReader>();

	protected Attribute(byte tag, int name_index, int length, ConstantPool constant_pool) {
		this.tag = tag;
		this.name_index = name_index;
		this.length = length;
		this.constant_pool = constant_pool;
	}

	@Override
	public abstract void accept(Visitor visitor);

	public void dump(DataOutputStream file) throws IOException {
		file.writeShort(name_index);
		file.writeInt(length);
	}

	public static void addAttributeReader(String name, AttributeReader r) {
		readers.put(name, r);
	}

	public static void removeAttributeReader(String name) {
		readers.remove(name);
	}

	public static final Attribute readAttribute(DataInputStream file, ConstantPool constant_pool)
			throws IOException, ClassFormatException {
		byte tag = -1;
		final int name_index = file.readUnsignedShort();
		final ConstantUtf8 c = (ConstantUtf8) constant_pool.getConstant(name_index, (byte) 1);
		final String name = c.getBytes();
		final int length = file.readInt();
		for (byte i = 0; i < 20; i++) {
			if (name.equals(Constants.ATTRIBUTE_NAMES[i])) {
				tag = i;
				break;
			}
		}
		switch (tag) {
		case -1: {
			final AttributeReader r = (AttributeReader) readers.get(name);
			if (r != null)
				return r.createAttribute(name_index, length, file, constant_pool);
			return new Unknown(name_index, length, file, constant_pool);
		}
		case 1:
			return new ConstantValue(name_index, length, file, constant_pool);
		case 0:
			return new SourceFile(name_index, length, file, constant_pool);
		case 2:
			return new Code(name_index, length, file, constant_pool);
		case 3:
			return new ExceptionTable(name_index, length, file, constant_pool);
		case 4:
			return new LineNumberTable(name_index, length, file, constant_pool);
		case 5:
			return new LocalVariableTable(name_index, length, file, constant_pool);
		case 6:
			return new InnerClasses(name_index, length, file, constant_pool);
		case 7:
			return new Synthetic(name_index, length, file, constant_pool);
		case 8:
			return new Deprecated(name_index, length, file, constant_pool);
		case 9:
			return new PMGClass(name_index, length, file, constant_pool);
		case 10:
			return new Signature(name_index, length, file, constant_pool);
		case 11:
			return new StackMap(name_index, length, file, constant_pool);
		case 12:
			return new RuntimeVisibleAnnotations(name_index, length, file, constant_pool);
		case 13:
			return new RuntimeInvisibleAnnotations(name_index, length, file, constant_pool);
		case 14:
			return new RuntimeVisibleParameterAnnotations(name_index, length, file, constant_pool);
		case 15:
			return new RuntimeInvisibleParameterAnnotations(name_index, length, file, constant_pool);
		case 16:
			return new AnnotationDefault(name_index, length, file, constant_pool);
		case 17:
			return new LocalVariableTypeTable(name_index, length, file, constant_pool);
		case 18:
			return new EnclosingMethod(name_index, length, file, constant_pool);
		case 19:
			return new StackMapTable(name_index, length, file, constant_pool);
		default:
			throw new IllegalStateException(
					new StringBuilder().append("Unrecognized attribute type tag parsed: ").append(tag).toString());
		}
	}

	public String getName() {
		final ConstantUtf8 c = (ConstantUtf8) constant_pool.getConstant(name_index, (byte) 1);
		return c.getBytes();
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int length) {
		this.length = length;
	}

	public final void setNameIndex(int name_index) {
		this.name_index = name_index;
	}

	public final int getNameIndex() {
		return name_index;
	}

	public final byte getTag() {
		return tag;
	}

	public final ConstantPool getConstantPool() {
		return constant_pool;
	}

	public final void setConstantPool(ConstantPool constant_pool) {
		this.constant_pool = constant_pool;
	}

	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public abstract Attribute copy(ConstantPool constantpool);

	@Override
	public String toString() {
		return Constants.ATTRIBUTE_NAMES[tag];
	}
}
