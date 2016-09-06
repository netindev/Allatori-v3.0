package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class INVOKESTATIC extends InvokeInstruction {
	private static final long serialVersionUID = -2160020248508943620L;

	INVOKESTATIC() {
	}

	public INVOKESTATIC(int index) {
		super((short) 184, index);
	}

	@Override
	public Class<?>[] getExceptions() {
		final Class<?>[] cs = new Class[2 + (ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length];
		System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0,
				(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length
				+ 1] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
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
		v.visitINVOKESTATIC(this);
	}
}
