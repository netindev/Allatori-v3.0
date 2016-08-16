package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class PUTFIELD extends FieldInstruction implements PopInstruction, ExceptionThrower {
	private static final long serialVersionUID = -3931392044558815011L;

	PUTFIELD() {
	}

	public PUTFIELD(int index) {
		super((short) 181, index);
	}

	public int consumeStack(ConstantPoolGen cpg) {
		return getFieldSize(cpg) + 1;
	}

	public Class[] getExceptions() {
		Class[] cs = new Class[2 + (ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length];
		System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0,
				(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length
				+ 1] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
		return cs;
	}

	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitStackConsumer(this);
		v.visitPopInstruction(this);
		v.visitTypedInstruction(this);
		v.visitLoadClass(this);
		v.visitCPInstruction(this);
		v.visitFieldOrMethod(this);
		v.visitFieldInstruction(this);
		v.visitPUTFIELD(this);
	}
}
