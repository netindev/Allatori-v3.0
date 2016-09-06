/* I2D - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class I2D extends ConversionInstruction {
	private static final long serialVersionUID = 4267924152721121331L;

	public I2D() {
		super((short) 135);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitI2D(this);
	}
}
