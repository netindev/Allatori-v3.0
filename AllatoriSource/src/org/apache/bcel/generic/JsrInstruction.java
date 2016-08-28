package org.apache.bcel.generic;

public abstract class JsrInstruction extends BranchInstruction
		implements UnconditionalBranch, TypedInstruction, StackProducer {
	private static final long serialVersionUID = -6438850002848773481L;

	JsrInstruction(short opcode, InstructionHandle target) {
		super(opcode, target);
	}

	JsrInstruction() {
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return new ReturnaddressType(physicalSuccessor());
	}

	public InstructionHandle physicalSuccessor() {
		InstructionHandle ih;
		for (ih = target; ih.getPrev() != null; ih = ih.getPrev()) {
		}
		for (; ih.getInstruction() != this; ih = ih.getNext()) {
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
