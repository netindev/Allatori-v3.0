package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class IREM extends ArithmeticInstruction implements ExceptionThrower {
	private static final long serialVersionUID = 834504719616513910L;

	public IREM() {
		super((short) 112);
	}

	@Override
	public Class<?>[] getExceptions() {
		return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitIREM(this);
	}
}
