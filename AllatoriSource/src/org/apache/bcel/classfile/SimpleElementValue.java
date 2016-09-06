/* SimpleElementValue - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleElementValue extends ElementValue {
	private int index;

	public SimpleElementValue(int type, int index, ConstantPool cpool) {
		super(type, cpool);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValueString() {
		if (type != 115)
			throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
		final ConstantUtf8 c = (ConstantUtf8) cpool.getConstant(getIndex(), (byte) 1);
		return c.getBytes();
	}

	public int getValueInt() {
		if (type != 73)
			throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
		final ConstantInteger c = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
		return c.getBytes();
	}

	public byte getValueByte() {
		if (type != 66)
			throw new RuntimeException("Dont call getValueByte() on a non BYTE ElementValue");
		final ConstantInteger c = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
		return (byte) c.getBytes();
	}

	public char getValueChar() {
		if (type != 67)
			throw new RuntimeException("Dont call getValueChar() on a non CHAR ElementValue");
		final ConstantInteger c = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
		return (char) c.getBytes();
	}

	public long getValueLong() {
		if (type != 74)
			throw new RuntimeException("Dont call getValueLong() on a non LONG ElementValue");
		final ConstantLong j = (ConstantLong) cpool.getConstant(getIndex());
		return j.getBytes();
	}

	public float getValueFloat() {
		if (type != 70)
			throw new RuntimeException("Dont call getValueFloat() on a non FLOAT ElementValue");
		final ConstantFloat f = (ConstantFloat) cpool.getConstant(getIndex());
		return f.getBytes();
	}

	public double getValueDouble() {
		if (type != 68)
			throw new RuntimeException("Dont call getValueDouble() on a non DOUBLE ElementValue");
		final ConstantDouble d = (ConstantDouble) cpool.getConstant(getIndex());
		return d.getBytes();
	}

	public boolean getValueBoolean() {
		if (type != 90)
			throw new RuntimeException("Dont call getValueBoolean() on a non BOOLEAN ElementValue");
		final ConstantInteger bo = (ConstantInteger) cpool.getConstant(getIndex());
		return bo.getBytes() != 0;
	}

	public short getValueShort() {
		if (type != 83)
			throw new RuntimeException("Dont call getValueShort() on a non SHORT ElementValue");
		final ConstantInteger s = (ConstantInteger) cpool.getConstant(getIndex());
		return (short) s.getBytes();
	}

	@Override
	public String toString() {
		return stringifyValue();
	}

	@Override
	public String stringifyValue() {
		switch (type) {
		case 73: {
			final ConstantInteger c = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
			return Integer.toString(c.getBytes());
		}
		case 74: {
			final ConstantLong j = (ConstantLong) cpool.getConstant(getIndex(), (byte) 5);
			return Long.toString(j.getBytes());
		}
		case 68: {
			final ConstantDouble d = (ConstantDouble) cpool.getConstant(getIndex(), (byte) 6);
			return Double.toString(d.getBytes());
		}
		case 70: {
			final ConstantFloat f = (ConstantFloat) cpool.getConstant(getIndex(), (byte) 4);
			return Float.toString(f.getBytes());
		}
		case 83: {
			final ConstantInteger s = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
			return Integer.toString(s.getBytes());
		}
		case 66: {
			final ConstantInteger b = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
			return Integer.toString(b.getBytes());
		}
		case 67: {
			final ConstantInteger ch = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
			return String.valueOf((char) ch.getBytes());
		}
		case 90: {
			final ConstantInteger bo = (ConstantInteger) cpool.getConstant(getIndex(), (byte) 3);
			if (bo.getBytes() == 0)
				return "false";
			return "true";
		}
		case 115: {
			final ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(getIndex(), (byte) 1);
			return cu8.getBytes();
		}
		default:
			throw new RuntimeException(new StringBuilder()
					.append("SimpleElementValue class does not know how to stringify type ").append(type).toString());
		}
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		switch (type) {
		case 66:
		case 67:
		case 68:
		case 70:
		case 73:
		case 74:
		case 83:
		case 90:
		case 115:
			dos.writeShort(getIndex());
			break;
		default:
			throw new RuntimeException(new StringBuilder()
					.append("SimpleElementValue doesnt know how to write out type ").append(type).toString());
		}
	}
}
