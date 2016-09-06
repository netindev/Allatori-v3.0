/* IF_ICMPLE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IF_ICMPLE extends IfInstruction {
	private static final long serialVersionUID = -6863915647505504868L;

	IF_ICMPLE() {
		/* empty */
	}

	public IF_ICMPLE(InstructionHandle target) {
		super((short) 164, target);
	}

	@Override
	public IfInstruction negate() {
		return new IF_ICMPGT(target);
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackConsumer(this);
		v.visitBranchInstruction(this);
		v.visitIfInstruction(this);
		v.visitIF_ICMPLE(this);
	}
}
