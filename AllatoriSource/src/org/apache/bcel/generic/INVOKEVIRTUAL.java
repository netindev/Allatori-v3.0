package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class INVOKEVIRTUAL extends InvokeInstruction {
	private static final long serialVersionUID = -7355623307528443725L;

	INVOKEVIRTUAL() {
	}

	public INVOKEVIRTUAL(int index) {
		super((short) 182, index);
	}

	@Override
	public Class<?>[] getExceptions() {
		final Class<?>[] cs = new Class[4 + (ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length];
		System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0,
				(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 3] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 2] = ExceptionConstants.ABSTRACT_METHOD_ERROR;
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length
				+ 1] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
		return cs;
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackConsumer(this);
		v.visitStackProducer(this);
		v.visitLoadClass(this);
		v.visitCPInstruction(this);
		v.visitFieldOrMethod(this);
		v.visitInvokeInstruction(this);
		v.visitINVOKEVIRTUAL(this);
	}
}
