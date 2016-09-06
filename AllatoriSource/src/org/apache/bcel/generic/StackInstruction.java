/* StackInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public abstract class StackInstruction extends Instruction {
	private static final long serialVersionUID = -8542057001831958935L;

	StackInstruction() {
		/* empty */
	}

	protected StackInstruction(short opcode) {
		super(opcode, (short) 1);
	}

	public Type getType(ConstantPoolGen cp) {
		return Type.UNKNOWN;
	}
}
