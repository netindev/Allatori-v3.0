/* DSUB - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DSUB extends ArithmeticInstruction {
	private static final long serialVersionUID = -5398969227995149466L;

	public DSUB() {
		super((short) 103);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitDSUB(this);
	}
}
