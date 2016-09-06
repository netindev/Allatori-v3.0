/* IFGE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IFGE extends IfInstruction {
	private static final long serialVersionUID = 8975527282985945729L;

	IFGE() {
		/* empty */
	}

	public IFGE(InstructionHandle target) {
		super((short) 156, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFLT(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFGE(this);
	}
}
