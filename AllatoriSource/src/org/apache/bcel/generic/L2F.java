/* L2F - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class L2F extends ConversionInstruction {
	private static final long serialVersionUID = -7181529985408901328L;

	public L2F() {
		super((short) 137);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitL2F(this);
	}
}
