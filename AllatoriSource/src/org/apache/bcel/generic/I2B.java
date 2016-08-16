package org.apache.bcel.generic;

public class I2B extends ConversionInstruction {
	private static final long serialVersionUID = -260969101469366410L;

	public I2B() {
		super((short) 145);
	}

	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitI2B(this);
	}
}
