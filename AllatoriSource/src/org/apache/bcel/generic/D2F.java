/* D2F - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class D2F extends ConversionInstruction {
	private static final long serialVersionUID = -448595874334076240L;

	public D2F() {
		super((short) 144);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitD2F(this);
	}
}
