/* DREM - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DREM extends ArithmeticInstruction {
	private static final long serialVersionUID = -3984082114153729887L;

	public DREM() {
		super((short) 115);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitDREM(this);
	}
}
