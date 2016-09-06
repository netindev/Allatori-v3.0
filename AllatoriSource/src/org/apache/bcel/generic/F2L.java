/* F2L - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class F2L extends ConversionInstruction {
	private static final long serialVersionUID = -5583947322933513819L;

	public F2L() {
		super((short) 140);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitF2L(this);
	}
}
