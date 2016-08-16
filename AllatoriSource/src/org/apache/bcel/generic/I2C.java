package org.apache.bcel.generic;

public class I2C extends ConversionInstruction {
	private static final long serialVersionUID = 7396507741159927455L;

	public I2C() {
		super((short) 146);
	}

	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitI2C(this);
	}
}
