/* ICONST - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class ICONST extends Instruction implements ConstantPushInstruction {
	private static final long serialVersionUID = -174595933747784635L;
	private int value;

	ICONST() {
		/* empty */
	}

	public ICONST(int i) {
		super((short) 3, (short) 1);
		if (i >= -1 && i <= 5)
			opcode = (short) (3 + i);
		else
			throw new ClassGenException(new StringBuilder()
					.append("ICONST can be used only for value between -1 and 5: ").append(i).toString());
		value = i;
	}

	@Override
	public Number getValue() {
		return Integer.valueOf(value);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		return Type.INT;
	}

	@Override
	public void accept(Visitor v) {
		v.visitPushInstruction(this);
		v.visitStackProducer(this);
		v.visitTypedInstruction(this);
		v.visitConstantPushInstruction(this);
		v.visitICONST(this);
	}
}
