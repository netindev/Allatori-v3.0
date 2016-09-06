/* L2I - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class L2I extends ConversionInstruction {
	private static final long serialVersionUID = -3263285222028195535L;

	public L2I() {
		super((short) 136);
	}

	@Override
	public void accept(Visitor v) {
		v.visitTypedInstruction(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitConversionInstruction(this);
		v.visitL2I(this);
	}
}
