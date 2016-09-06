/* DUP2_X2 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DUP2_X2 extends StackInstruction {
	private static final long serialVersionUID = 1182584253776211326L;

	public DUP2_X2() {
		super((short) 94);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackInstruction(this);
		v.visitDUP2_X2(this);
	}
}
