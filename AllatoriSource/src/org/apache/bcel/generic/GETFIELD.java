package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class GETFIELD extends FieldInstruction implements ExceptionThrower, StackConsumer, StackProducer {
	private static final long serialVersionUID = -6510928743515082496L;

	GETFIELD() {
	}

	public GETFIELD(int index) {
		super((short) 180, index);
	}

	@Override
	public int produceStack(ConstantPoolGen cpg) {
		return getFieldSize(cpg);
	}

	@Override
	public Class[] getExceptions() {
		final Class[] cs = new Class[2 + (ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length];
		System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0,
				(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length
				+ 1] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
		return cs;
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitStackConsumer(this);
		v.visitStackProducer(this);
		v.visitTypedInstruction(this);
		v.visitLoadClass(this);
		v.visitCPInstruction(this);
		v.visitFieldOrMethod(this);
		v.visitFieldInstruction(this);
		v.visitGETFIELD(this);
	}
}
