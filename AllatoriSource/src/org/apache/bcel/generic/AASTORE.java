/* AASTORE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class AASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = -6440799431970565816L;

	public AASTORE() {
		super((short) 83);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitAASTORE(this);
	}
}
