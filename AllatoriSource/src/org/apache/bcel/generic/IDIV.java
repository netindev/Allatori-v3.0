package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class IDIV extends ArithmeticInstruction implements ExceptionThrower {
	private static final long serialVersionUID = -7104037931993634172L;

	public IDIV() {
		super((short) 108);
	}

	@Override
	public Class[] getExceptions() {
		return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
	}

	@Override
	public void accept(Visitor v) {
		v.visitExceptionThrower(this);
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitArithmeticInstruction(this);
		v.visitIDIV(this);
	}
}
