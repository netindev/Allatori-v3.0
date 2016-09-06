/* ReturnInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public abstract class ReturnInstruction extends Instruction
		implements ExceptionThrower, TypedInstruction, StackConsumer {
	private static final long serialVersionUID = -1248578537710620153L;

	ReturnInstruction() {
		/* empty */
	}

	protected ReturnInstruction(short opcode) {
		super(opcode, (short) 1);
	}

	public Type getType() {
		switch (opcode) {
		case 172:
			return Type.INT;
		case 173:
			return Type.LONG;
		case 174:
			return Type.FLOAT;
		case 175:
			return Type.DOUBLE;
		case 176:
			return Type.OBJECT;
		case 177:
			return Type.VOID;
		default:
			throw new ClassGenException(new StringBuilder().append("Unknown type ").append(opcode).toString());
		}
	}

	@Override
	public Class[] getExceptions() {
		return new Class[] { ExceptionConstants.ILLEGAL_MONITOR_STATE };
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return getType();
	}
}
