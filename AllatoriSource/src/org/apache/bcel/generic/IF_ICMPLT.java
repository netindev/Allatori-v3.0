/* IF_ICMPLT - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IF_ICMPLT extends IfInstruction {
	private static final long serialVersionUID = -6835991395337462478L;

	IF_ICMPLT() {
		/* empty */
	}

	public IF_ICMPLT(InstructionHandle target) {
		super((short) 161, target);
	}

	@Override
	public IfInstruction negate() {
		return new IF_ICMPGE(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIF_ICMPLT(this);
	}
}
