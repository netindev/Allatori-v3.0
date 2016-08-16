package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.util.ByteSequence;

public abstract class Instruction implements Cloneable, Serializable {
	private static final long serialVersionUID = -2518741982574515847L;
	protected short length = 1;
	protected short opcode = -1;
	private static InstructionComparator cmp = InstructionComparator.DEFAULT;

	Instruction() {
	}

	public Instruction(short opcode, short length) {
		this.length = length;
		this.opcode = opcode;
	}

	public void dump(DataOutputStream out) throws IOException {
		out.writeByte(opcode);
	}

	public String getName() {
		return Constants.OPCODE_NAMES[opcode];
	}

	public String toString(boolean verbose) {
		if (verbose)
			return new StringBuilder().append(getName()).append("[").append(opcode).append("](").append(length)
					.append(")").toString();
		return getName();
	}

	public String toString() {
		return toString(true);
	}

	public String toString(ConstantPool cp) {
		return toString(false);
	}

	public Instruction copy() {
		Instruction i = null;
		if (InstructionConstants.INSTRUCTIONS[getOpcode()] != null)
			i = this;
		else {
			try {
				i = (Instruction) this.clone();
			} catch (CloneNotSupportedException e) {
				System.err.println(e);
			}
		}
		return i;
	}

	protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
	}

	public static final Instruction readInstruction(ByteSequence bytes) throws IOException {
		boolean wide = false;
		short opcode = (short) bytes.readUnsignedByte();
		Instruction obj = null;
		if (opcode == 196) {
			wide = true;
			opcode = (short) bytes.readUnsignedByte();
		}
		if (InstructionConstants.INSTRUCTIONS[opcode] != null)
			return InstructionConstants.INSTRUCTIONS[opcode];
		switch (opcode) {
		case 16:
			obj = new BIPUSH();
			break;
		case 17:
			obj = new SIPUSH();
			break;
		case 18:
			obj = new LDC();
			break;
		case 19:
			obj = new LDC_W();
			break;
		case 20:
			obj = new LDC2_W();
			break;
		case 21:
			obj = new ILOAD();
			break;
		case 22:
			obj = new LLOAD();
			break;
		case 23:
			obj = new FLOAD();
			break;
		case 24:
			obj = new DLOAD();
			break;
		case 25:
			obj = new ALOAD();
			break;
		case 26:
			obj = new ILOAD(0);
			break;
		case 27:
			obj = new ILOAD(1);
			break;
		case 28:
			obj = new ILOAD(2);
			break;
		case 29:
			obj = new ILOAD(3);
			break;
		case 30:
			obj = new LLOAD(0);
			break;
		case 31:
			obj = new LLOAD(1);
			break;
		case 32:
			obj = new LLOAD(2);
			break;
		case 33:
			obj = new LLOAD(3);
			break;
		case 34:
			obj = new FLOAD(0);
			break;
		case 35:
			obj = new FLOAD(1);
			break;
		case 36:
			obj = new FLOAD(2);
			break;
		case 37:
			obj = new FLOAD(3);
			break;
		case 38:
			obj = new DLOAD(0);
			break;
		case 39:
			obj = new DLOAD(1);
			break;
		case 40:
			obj = new DLOAD(2);
			break;
		case 41:
			obj = new DLOAD(3);
			break;
		case 42:
			obj = new ALOAD(0);
			break;
		case 43:
			obj = new ALOAD(1);
			break;
		case 44:
			obj = new ALOAD(2);
			break;
		case 45:
			obj = new ALOAD(3);
			break;
		case 54:
			obj = new ISTORE();
			break;
		case 55:
			obj = new LSTORE();
			break;
		case 56:
			obj = new FSTORE();
			break;
		case 57:
			obj = new DSTORE();
			break;
		case 58:
			obj = new ASTORE();
			break;
		case 59:
			obj = new ISTORE(0);
			break;
		case 60:
			obj = new ISTORE(1);
			break;
		case 61:
			obj = new ISTORE(2);
			break;
		case 62:
			obj = new ISTORE(3);
			break;
		case 63:
			obj = new LSTORE(0);
			break;
		case 64:
			obj = new LSTORE(1);
			break;
		case 65:
			obj = new LSTORE(2);
			break;
		case 66:
			obj = new LSTORE(3);
			break;
		case 67:
			obj = new FSTORE(0);
			break;
		case 68:
			obj = new FSTORE(1);
			break;
		case 69:
			obj = new FSTORE(2);
			break;
		case 70:
			obj = new FSTORE(3);
			break;
		case 71:
			obj = new DSTORE(0);
			break;
		case 72:
			obj = new DSTORE(1);
			break;
		case 73:
			obj = new DSTORE(2);
			break;
		case 74:
			obj = new DSTORE(3);
			break;
		case 75:
			obj = new ASTORE(0);
			break;
		case 76:
			obj = new ASTORE(1);
			break;
		case 77:
			obj = new ASTORE(2);
			break;
		case 78:
			obj = new ASTORE(3);
			break;
		case 132:
			obj = new IINC();
			break;
		case 153:
			obj = new IFEQ();
			break;
		case 154:
			obj = new IFNE();
			break;
		case 155:
			obj = new IFLT();
			break;
		case 156:
			obj = new IFGE();
			break;
		case 157:
			obj = new IFGT();
			break;
		case 158:
			obj = new IFLE();
			break;
		case 159:
			obj = new IF_ICMPEQ();
			break;
		case 160:
			obj = new IF_ICMPNE();
			break;
		case 161:
			obj = new IF_ICMPLT();
			break;
		case 162:
			obj = new IF_ICMPGE();
			break;
		case 163:
			obj = new IF_ICMPGT();
			break;
		case 164:
			obj = new IF_ICMPLE();
			break;
		case 165:
			obj = new IF_ACMPEQ();
			break;
		case 166:
			obj = new IF_ACMPNE();
			break;
		case 167:
			obj = new GOTO();
			break;
		case 168:
			obj = new JSR();
			break;
		case 169:
			obj = new RET();
			break;
		case 170:
			obj = new TABLESWITCH();
			break;
		case 171:
			obj = new LOOKUPSWITCH();
			break;
		case 178:
			obj = new GETSTATIC();
			break;
		case 179:
			obj = new PUTSTATIC();
			break;
		case 180:
			obj = new GETFIELD();
			break;
		case 181:
			obj = new PUTFIELD();
			break;
		case 182:
			obj = new INVOKEVIRTUAL();
			break;
		case 183:
			obj = new INVOKESPECIAL();
			break;
		case 184:
			obj = new INVOKESTATIC();
			break;
		case 185:
			obj = new INVOKEINTERFACE();
			break;
		case 187:
			obj = new NEW();
			break;
		case 188:
			obj = new NEWARRAY();
			break;
		case 189:
			obj = new ANEWARRAY();
			break;
		case 192:
			obj = new CHECKCAST();
			break;
		case 193:
			obj = new INSTANCEOF();
			break;
		case 197:
			obj = new MULTIANEWARRAY();
			break;
		case 198:
			obj = new IFNULL();
			break;
		case 199:
			obj = new IFNONNULL();
			break;
		case 200:
			obj = new GOTO_W();
			break;
		case 201:
			obj = new JSR_W();
			break;
		case 202:
			obj = new BREAKPOINT();
			break;
		case 254:
			obj = new IMPDEP1();
			break;
		case 255:
			obj = new IMPDEP2();
			break;
		default:
			throw new ClassGenException(
					new StringBuilder().append("Illegal opcode detected: ").append(opcode).toString());
		}
		if (wide && !(obj instanceof LocalVariableInstruction) && !(obj instanceof IINC) && !(obj instanceof RET))
			throw new ClassGenException(
					new StringBuilder().append("Illegal opcode after wide: ").append(opcode).toString());
		obj.setOpcode(opcode);
		obj.initFromFile(bytes, wide);
		return obj;
	}

	public int consumeStack(ConstantPoolGen cpg) {
		return Constants.CONSUME_STACK[opcode];
	}

	public int produceStack(ConstantPoolGen cpg) {
		return Constants.PRODUCE_STACK[opcode];
	}

	public short getOpcode() {
		return opcode;
	}

	public int getLength() {
		return length;
	}

	private void setOpcode(short opcode) {
		this.opcode = opcode;
	}

	void dispose() {
	}

	public abstract void accept(Visitor visitor);

	/**
	 * @deprecated
	 */
	public static InstructionComparator getComparator() {
		return cmp;
	}

	/**
	 * @deprecated
	 */
	public static void setComparator(InstructionComparator c) {
		cmp = c;
	}

	public boolean equals(Object that) {
		return (that instanceof Instruction ? cmp.equals(this, (Instruction) that) : false);
	}
}
