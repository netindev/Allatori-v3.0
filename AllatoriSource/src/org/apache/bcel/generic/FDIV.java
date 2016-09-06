/* FDIV - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class FDIV extends ArithmeticInstruction {
	private static final long serialVersionUID = -8536341322669578097L;

	public FDIV() {
		super((short) 110);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitFDIV(this);
	}
}
