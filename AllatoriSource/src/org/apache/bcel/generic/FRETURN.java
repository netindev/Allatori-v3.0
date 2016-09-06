/* FRETURN - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class FRETURN extends ReturnInstruction {
	private static final long serialVersionUID = -3630453809574277966L;

	public FRETURN() {
		super((short) 174);
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackConsumer(this);
		v.visitReturnInstruction(this);
		v.visitFRETURN(this);
	}
}
