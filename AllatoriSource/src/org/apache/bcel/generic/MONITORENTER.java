package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class MONITORENTER extends Instruction implements ExceptionThrower, StackConsumer {
	private static final long serialVersionUID = 4537302966975402521L;

	public MONITORENTER() {
		super((short) 194, (short) 1);
	}

	@Override
	public Class[] getExceptions() {
		return new Class[] { ExceptionConstants.NULL_POINTER_EXCEPTION };
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitStackConsumer(this);
		v.visitMONITORENTER(this);
	}
}
