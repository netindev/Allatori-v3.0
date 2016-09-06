/* BREAKPOINT - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class BREAKPOINT extends Instruction {
	private static final long serialVersionUID = -4186956277760244254L;

	public BREAKPOINT() {
		super((short) 202, (short) 1);
	}

	@Override
	public void accept(Visitor v) {
		v.visitBREAKPOINT(this);
	}
}
