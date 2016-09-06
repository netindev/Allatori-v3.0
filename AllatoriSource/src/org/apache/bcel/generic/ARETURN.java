/* ARETURN - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class ARETURN extends ReturnInstruction {
	private static final long serialVersionUID = -3497286197421151311L;

	public ARETURN() {
		super((short) 176);
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackConsumer(this);
		v.visitReturnInstruction(this);
		v.visitARETURN(this);
	}
}
