package org.apache.bcel.generic;

import java.io.Serializable;

import org.apache.bcel.classfile.CodeException;

public final class CodeExceptionGen implements InstructionTargeter, Cloneable, Serializable {
	private static final long serialVersionUID = 6548901422158960190L;
	private InstructionHandle start_pc;
	private InstructionHandle end_pc;
	private InstructionHandle handler_pc;
	private ObjectType catch_type;

	public CodeExceptionGen(InstructionHandle start_pc, InstructionHandle end_pc, InstructionHandle handler_pc,
			ObjectType catch_type) {
		setStartPC(start_pc);
		setEndPC(end_pc);
		setHandlerPC(handler_pc);
		this.catch_type = catch_type;
	}

	public CodeException getCodeException(ConstantPoolGen cp) {
		return new CodeException(start_pc.getPosition(), (end_pc.getPosition() + end_pc.getInstruction().getLength()),
				handler_pc.getPosition(), (catch_type == null ? 0 : cp.addClass(catch_type)));
	}

	public void setStartPC(InstructionHandle start_pc) {
		BranchInstruction.notifyTarget(this.start_pc, start_pc, this);
		this.start_pc = start_pc;
	}

	public void setEndPC(InstructionHandle end_pc) {
		BranchInstruction.notifyTarget(this.end_pc, end_pc, this);
		this.end_pc = end_pc;
	}

	public void setHandlerPC(InstructionHandle handler_pc) {
		BranchInstruction.notifyTarget(this.handler_pc, handler_pc, this);
		this.handler_pc = handler_pc;
	}

	public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
		boolean targeted = false;
		if (start_pc == old_ih) {
			targeted = true;
			setStartPC(new_ih);
		}
		if (end_pc == old_ih) {
			targeted = true;
			setEndPC(new_ih);
		}
		if (handler_pc == old_ih) {
			targeted = true;
			setHandlerPC(new_ih);
		}
		if (!targeted)
			throw new ClassGenException(
					new StringBuilder().append("Not targeting ").append(old_ih).append(", but {").append(start_pc)
							.append(", ").append(end_pc).append(", ").append(handler_pc).append("}").toString());
	}

	public boolean containsTarget(InstructionHandle ih) {
		return start_pc == ih || end_pc == ih || handler_pc == ih;
	}

	public void setCatchType(ObjectType catch_type) {
		this.catch_type = catch_type;
	}

	public ObjectType getCatchType() {
		return catch_type;
	}

	public InstructionHandle getStartPC() {
		return start_pc;
	}

	public InstructionHandle getEndPC() {
		return end_pc;
	}

	public InstructionHandle getHandlerPC() {
		return handler_pc;
	}

	public String toString() {
		return new StringBuilder().append("CodeExceptionGen(").append(start_pc).append(", ").append(end_pc).append(", ")
				.append(handler_pc).append(")").toString();
	}

	public Object clone() {
		Object object;
		try {
			object = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(e);
			return null;
		}
		return object;
	}
}
