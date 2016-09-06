/* ISHL - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class ISHL extends ArithmeticInstruction {
	private static final long serialVersionUID = -7440998118555505042L;

	public ISHL() {
		super((short) 120);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitISHL(this);
	}
}
