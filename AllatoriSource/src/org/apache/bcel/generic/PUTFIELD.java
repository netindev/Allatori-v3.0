/* PUTFIELD - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class PUTFIELD extends FieldInstruction implements PopInstruction, ExceptionThrower {
	private static final long serialVersionUID = -3931392044558815011L;

	PUTFIELD() {
		/* empty */
	}

	public PUTFIELD(int index) {
		super((short) 181, index);
	}

	@Override
	public int consumeStack(ConstantPoolGen cpg) {
		return getFieldSize(cpg) + 1;
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
		v.visitPopInstruction(this);
		v.visitTypedInstruction(this);
		v.visitLoadClass(this);
		v.visitCPInstruction(this);
		v.visitFieldOrMethod(this);
		v.visitFieldInstruction(this);
		v.visitPUTFIELD(this);
	}
}
