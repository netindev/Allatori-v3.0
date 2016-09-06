/* ConversionInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public abstract class ConversionInstruction extends Instruction
		implements TypedInstruction, StackProducer, StackConsumer {
	private static final long serialVersionUID = -4283015897133699172L;

	ConversionInstruction() {
		/* empty */
	}

	protected ConversionInstruction(short opcode) {
		super(opcode, (short) 1);
	}

	@Override
	public Type getType(ConstantPoolGen cp) {
		switch (opcode) {
		case 136:
		case 139:
		case 142:
			return Type.INT;
		case 134:
		case 137:
		case 144:
			return Type.FLOAT;
		case 133:
		case 140:
		case 143:
			return Type.LONG;
		case 135:
		case 138:
		case 141:
			return Type.DOUBLE;
		case 145:
			return Type.BYTE;
		case 146:
			return Type.CHAR;
		case 147:
			return Type.SHORT;
		default:
			throw new ClassGenException(new StringBuilder().append("Unknown type ").append(opcode).toString());
		}
	}
}
