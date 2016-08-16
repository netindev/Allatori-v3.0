package org.apache.bcel.generic;

public class I2F extends ConversionInstruction {
	private static final long serialVersionUID = -816875455957376859L;

	public I2F() {
		super((short) 134);
	}

	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitI2F(this);
	}
}
