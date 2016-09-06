/* IASTORE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IASTORE extends ArrayInstruction implements StackConsumer {
	private static final long serialVersionUID = -3570157505504012648L;

	public IASTORE() {
		super((short) 79);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitIASTORE(this);
	}
}
