/* DMUL - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DMUL extends ArithmeticInstruction {
	private static final long serialVersionUID = 7491480641611951432L;

	public DMUL() {
		super((short) 107);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitDMUL(this);
	}
}
