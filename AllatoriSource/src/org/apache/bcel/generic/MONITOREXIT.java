package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class MONITOREXIT extends Instruction implements ExceptionThrower, StackConsumer {
	private static final long serialVersionUID = -1499496577099659601L;

	public MONITOREXIT() {
		super((short) 195, (short) 1);
	}

	@Override
	public Class[] getExceptions() {
		return new Class[] { ExceptionConstants.NULL_POINTER_EXCEPTION };
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitStackConsumer(this);
		v.visitMONITOREXIT(this);
	}
}
