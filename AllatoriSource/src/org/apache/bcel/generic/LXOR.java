/* LXOR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LXOR extends ArithmeticInstruction {
	private static final long serialVersionUID = -3031187042785170579L;

	public LXOR() {
		super((short) 131);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitLXOR(this);
	}
}
