/* FALOAD - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class FALOAD extends ArrayInstruction implements StackProducer {
	private static final long serialVersionUID = 3369925718821219472L;

	public FALOAD() {
		super((short) 48);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitFALOAD(this);
	}
}
