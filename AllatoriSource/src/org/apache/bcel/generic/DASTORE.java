/* DASTORE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = 5236493427411303394L;

	public DASTORE() {
		super((short) 82);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitDASTORE(this);
	}
}
