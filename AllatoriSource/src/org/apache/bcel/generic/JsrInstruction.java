/* JsrInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public abstract class JsrInstruction extends BranchInstruction
		implements UnconditionalBranch, TypedInstruction, StackProducer {
	private static final long serialVersionUID = -6438850002848773481L;

	JsrInstruction(short opcode, InstructionHandle target) {
		super(opcode, target);
	}

	JsrInstruction() {
		/* empty */
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return new ReturnaddressType(physicalSuccessor());
	}

	public InstructionHandle physicalSuccessor() {
		InstructionHandle ih;
		for (ih = target; ih.getPrev() != null; ih = ih.getPrev()) {
			/* empty */
		}
		for (/**/; ih.getInstruction() != this; ih = ih.getNext()) {
			/* empty */
		}
		final InstructionHandle toThis = ih;
		while_47_: do {
			do {
				if (ih == null)
					break while_47_;
				ih = ih.getNext();
			} while (ih == null || ih.getInstruction() != this);
			throw new RuntimeException("physicalSuccessor() called on a shared JsrInstruction.");
		} while (false);
		return toThis.getNext();
	}
}
