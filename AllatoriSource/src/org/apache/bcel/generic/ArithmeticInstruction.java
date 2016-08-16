package org.apache.bcel.generic;

public abstract class ArithmeticInstruction extends Instruction
		implements TypedInstruction, StackProducer, StackConsumer {
	private static final long serialVersionUID = 5027221136148765144L;

	ArithmeticInstruction() {
	}

	protected ArithmeticInstruction(short opcode) {
		super(opcode, (short) 1);
	}

	public Type getType(ConstantPoolGen cp) {
		switch (opcode) {
		case 99:
		case 103:
		case 107:
		case 111:
		case 115:
		case 119:
			return Type.DOUBLE;
		case 98:
		case 102:
		case 106:
		case 110:
		case 114:
		case 118:
			return Type.FLOAT;
		case 96:
		case 100:
		case 104:
		case 108:
		case 112:
		case 116:
		case 120:
		case 122:
		case 124:
		case 126:
		case 128:
		case 130:
			return Type.INT;
		case 97:
		case 101:
		case 105:
		case 109:
		case 113:
		case 117:
		case 121:
		case 123:
		case 125:
		case 127:
		case 129:
		case 131:
			return Type.LONG;
		default:
			throw new ClassGenException(new StringBuilder().append("Unknown type ").append(opcode).toString());
		}
	}
}
