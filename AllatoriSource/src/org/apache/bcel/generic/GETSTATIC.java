package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class GETSTATIC extends FieldInstruction implements PushInstruction, ExceptionThrower {
	private static final long serialVersionUID = -477185594622953478L;

	GETSTATIC() {
	}

	public GETSTATIC(int index) {
		super((short) 178, index);
	}

	@Override
	public int produceStack(ConstantPoolGen cpg) {
		return getFieldSize(cpg);
	}

	@Override
	public Class[] getExceptions() {
		final Class[] cs = new Class[1 + (ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length];
		System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0,
				(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
		return cs;
	}

	@Override
	public void accept(Visitor v) {
		v.visitStackProducer(this);
		v.visitPushInstruction(this);
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitLoadClass(this);
		v.visitCPInstruction(this);
		v.visitFieldOrMethod(this);
		v.visitFieldInstruction(this);
		v.visitGETSTATIC(this);
	}
}
