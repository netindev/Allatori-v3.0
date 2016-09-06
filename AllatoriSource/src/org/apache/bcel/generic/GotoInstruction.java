/* GotoInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public abstract class GotoInstruction extends BranchInstruction implements UnconditionalBranch {
	private static final long serialVersionUID = -2882435228056875173L;

	GotoInstruction(short opcode, InstructionHandle target) {
		super(opcode, target);
	}

	GotoInstruction() {
		/* empty */
	}
}
