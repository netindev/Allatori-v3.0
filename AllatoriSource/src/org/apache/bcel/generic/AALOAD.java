/* AALOAD - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class AALOAD extends ArrayInstruction implements StackProducer {
	private static final long serialVersionUID = -8606835203239531080L;

	public AALOAD() {
		super((short) 50);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitArrayInstruction(this);
		v.visitAALOAD(this);
	}
}
