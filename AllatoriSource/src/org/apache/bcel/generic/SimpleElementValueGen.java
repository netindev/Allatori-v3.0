package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.SimpleElementValue;

public class SimpleElementValueGen extends ElementValueGen {
	public void setIndex(int idx) {
		this.idx = idx;
	}

	private int idx;

	protected SimpleElementValueGen(int type, int idx, ConstantPoolGen cpGen) {
		super(type, cpGen);
		this.idx = idx;
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, int value) {
		super(type, cpGen);
		idx = cpGen.addInteger(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, long value) {
		super(type, cpGen);
		idx = cpGen.addLong(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, double value) {
		super(type, cpGen);
		idx = cpGen.addDouble(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, float value) {
		super(type, cpGen);
		idx = cpGen.addFloat(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, short value) {
		super(type, cpGen);
		idx = cpGen.addInteger(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, byte value) {
		super(type, cpGen);
		idx = cpGen.addInteger(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, char value) {
		super(type, cpGen);
		idx = cpGen.addInteger(value);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, boolean value) {
		super(type, cpGen);
		if (value)
			idx = cpGen.addInteger(1);
		else
			idx = cpGen.addInteger(0);
	}

	public SimpleElementValueGen(int type, ConstantPoolGen cpGen, String value) {
		super(type, cpGen);
		idx = cpGen.addUtf8(value);
	}

	public SimpleElementValueGen(SimpleElementValue value, ConstantPoolGen cpool, boolean copyPoolEntries) {
		super(value.getElementValueType(), cpool);
		if (!copyPoolEntries)
			idx = value.getIndex();
		else {
			switch (value.getElementValueType()) {
			case 115:
				idx = cpool.addUtf8(value.getValueString());
				break;
			case 73:
				idx = cpool.addInteger(value.getValueInt());
				break;
			case 66:
				idx = cpool.addInteger(value.getValueByte());
				break;
			case 67:
				idx = cpool.addInteger(value.getValueChar());
				break;
			case 74:
				idx = cpool.addLong(value.getValueLong());
				break;
			case 70:
				idx = cpool.addFloat(value.getValueFloat());
				break;
			case 68:
				idx = cpool.addDouble(value.getValueDouble());
				break;
			case 90:
				if (value.getValueBoolean())
					idx = cpool.addInteger(1);
				else
					idx = cpool.addInteger(0);
				break;
			case 83:
				idx = cpool.addInteger(value.getValueShort());
				break;
			default:
				throw new RuntimeException(
						new StringBuilder().append("SimpleElementValueGen class does not know how to copy this type ")
								.append(type).toString());
			}
		}
	}

	@Override
	public ElementValue getElementValue() {
		return new SimpleElementValue(type, idx, cpGen.getConstantPool());
	}

	public int getIndex() {
		return idx;
	}

	public String getValueString() {
		if (type != 115)
			throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
		final ConstantUtf8 c = (ConstantUtf8) cpGen.getConstant(idx);
		return c.getBytes();
	}

	public int getValueInt() {
		if (type != 73)
			throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
		final ConstantInteger c = (ConstantInteger) cpGen.getConstant(idx);
		return c.getBytes();
	}

	@Override
	public String stringifyValue() {
		switch (type) {
		case 73: {
			final ConstantInteger c = (ConstantInteger) cpGen.getConstant(idx);
			return Integer.toString(c.getBytes());
		}
		case 74: {
			final ConstantLong j = (ConstantLong) cpGen.getConstant(idx);
			return Long.toString(j.getBytes());
		}
		case 68: {
			final ConstantDouble d = (ConstantDouble) cpGen.getConstant(idx);
			return Double.toString(d.getBytes());
		}
		case 70: {
			final ConstantFloat f = (ConstantFloat) cpGen.getConstant(idx);
			return Float.toString(f.getBytes());
		}
		case 83: {
			final ConstantInteger s = (ConstantInteger) cpGen.getConstant(idx);
			return Integer.toString(s.getBytes());
		}
		case 66: {
			final ConstantInteger b = (ConstantInteger) cpGen.getConstant(idx);
			return Integer.toString(b.getBytes());
		}
		case 67: {
			final ConstantInteger ch = (ConstantInteger) cpGen.getConstant(idx);
			return Integer.toString(ch.getBytes());
		}
		case 90: {
			final ConstantInteger bo = (ConstantInteger) cpGen.getConstant(idx);
			if (bo.getBytes() == 0)
				return "false";
			return "true";
		}
		case 115: {
			final ConstantUtf8 cu8 = (ConstantUtf8) cpGen.getConstant(idx);
			return cu8.getBytes();
		}
		default:
			throw new RuntimeException(
					new StringBuilder().append("SimpleElementValueGen class does not know how to stringify type ")
							.append(type).toString());
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
			dos.writeShort(idx);
			break;
		default:
			throw new RuntimeException(new StringBuilder()
					.append("SimpleElementValueGen doesnt know how to write out type ").append(type).toString());
		}
	}
}
