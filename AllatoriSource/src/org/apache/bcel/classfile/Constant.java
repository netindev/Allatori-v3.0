package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.bcel.Constants;
import org.apache.bcel.util.BCELComparator;

public abstract class Constant implements Cloneable, Node, Serializable {
	private static final long serialVersionUID = 5739037344085356353L;
	private static BCELComparator _cmp = new BCELComparator() {
		@Override
		public boolean equals(Object o1, Object o2) {
			final Constant THIS = (Constant) o1;
			final Constant THAT = (Constant) o2;
			return THIS.toString().equals(THAT.toString());
		}

		@Override
		public int hashCode(Object o) {
			final Constant THIS = (Constant) o;
			return THIS.toString().hashCode();
		}
	};
	protected byte tag;

	Constant(byte tag) {
		this.tag = tag;
	}

	@Override
	public abstract void accept(Visitor visitor);

	public abstract void dump(DataOutputStream dataoutputstream) throws IOException;

	public final byte getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(Constants.CONSTANT_NAMES[tag]).append("[").append(tag).append("]").toString();
	}

	public Constant copy() {
		Constant constant;
		try {
			constant = (Constant) super.clone();
		} catch (final CloneNotSupportedException clonenotsupportedexception) {
			return null;
		}
		return constant;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	static final Constant readConstant(DataInputStream file) throws IOException, ClassFormatException {
		final byte b = file.readByte();
		switch (b) {
		case 7:
			return new ConstantClass(file);
		case 9:
			return new ConstantFieldref(file);
		case 10:
			return new ConstantMethodref(file);
		case 11:
			return new ConstantInterfaceMethodref(file);
		case 8:
			return new ConstantString(file);
		case 3:
			return new ConstantInteger(file);
		case 4:
			return new ConstantFloat(file);
		case 5:
			return new ConstantLong(file);
		case 6:
			return new ConstantDouble(file);
		case 12:
			return new ConstantNameAndType(file);
		case 1:
			return new ConstantUtf8(file);
		default:
			throw new ClassFormatException(
					new StringBuilder().append("Invalid byte tag in constant pool: ").append(b).toString());
		}
	}

	public static BCELComparator getComparator() {
		return _cmp;
	}

	public static void setComparator(BCELComparator comparator) {
		_cmp = comparator;
	}

	@Override
	public boolean equals(Object obj) {
		return _cmp.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return _cmp.hashCode(this);
	}
}
