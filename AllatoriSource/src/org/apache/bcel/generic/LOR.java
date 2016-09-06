/* LOR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LOR extends ArithmeticInstruction {
	private static final long serialVersionUID = 7862213176431021916L;

	public LOR() {
		super((short) 129);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitLOR(this);
	}
}
