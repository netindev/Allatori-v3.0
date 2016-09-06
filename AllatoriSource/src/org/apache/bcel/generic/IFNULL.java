/* IFNULL - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IFNULL extends IfInstruction {
	private static final long serialVersionUID = 608514554995424349L;

	IFNULL() {
		/* empty */
	}

	public IFNULL(InstructionHandle target) {
		super((short) 198, target);
	}

	@Override
	public IfInstruction negate() {
		return new IFNONNULL(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIFNULL(this);
	}
}
