/* LASTORE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = -6758326777570451990L;

	public LASTORE() {
		super((short) 80);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitLASTORE(this);
	}
}
