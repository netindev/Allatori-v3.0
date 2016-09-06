/* BasicType - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import org.apache.bcel.Constants;

public final class BasicType extends Type {
	private static final long serialVersionUID = -6546010740455512176L;

	BasicType(byte type) {
		super(type, Constants.SHORT_TYPE_NAMES[type]);
		if (type < 4 || type > 12)
			throw new ClassGenException(new StringBuilder().append("Invalid type: ").append(type).toString());
	}

	public static final BasicType getType(byte type) {
		switch (type) {
		case 12:
			return VOID;
		case 4:
			return BOOLEAN;
		case 8:
			return BYTE;
		case 9:
			return SHORT;
		case 5:
			return CHAR;
		case 10:
			return INT;
		case 11:
			return LONG;
		case 7:
			return DOUBLE;
		case 6:
			return FLOAT;
		default:
			throw new ClassGenException(new StringBuilder().append("Invalid type: ").append(type).toString());
		}
	}

	@Override
	public int hashCode() {
		return type;
	}

	@Override
	public boolean equals(Object _type) {
		return (_type instanceof BasicType ? ((BasicType) _type).type == type : false);
	}
}
