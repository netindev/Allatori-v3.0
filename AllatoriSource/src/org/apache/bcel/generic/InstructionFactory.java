/* InstructionFactory - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.io.Serializable;

public class InstructionFactory implements InstructionConstants, Serializable {
	private static final long serialVersionUID = -1210011499635580258L;
	private static final String[] short_names = { "C", "F", "D", "B", "S", "I", "L" };
	protected ClassGen cg;
	protected ConstantPoolGen cp;
	private static final MethodObject[] append_mos = {
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.STRING }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.OBJECT }), null,
			null, new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.BOOLEAN }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.CHAR }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.FLOAT }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.DOUBLE }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }),
			new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.LONG }) };

	private static class MethodObject {
		Type[] arg_types;
		Type result_type;
		String class_name;
		String name;

		MethodObject(String c, String n, Type r, Type[] a) {
			class_name = c;
			name = n;
			result_type = r;
			arg_types = a;
		}
	}

	public InstructionFactory(ClassGen cg, ConstantPoolGen cp) {
		this.cg = cg;
		this.cp = cp;
	}

	public InstructionFactory(ClassGen cg) {
		this(cg, cg.getConstantPool());
	}

	public InstructionFactory(ConstantPoolGen cp) {
		this(null, cp);
	}

	public InvokeInstruction createInvoke(String class_name, String name, Type ret_type, Type[] arg_types, short kind) {
		int nargs = 0;
		final String signature = Type.getMethodSignature(ret_type, arg_types);
		for (int i = 0; i < arg_types.length; i++)
			nargs += arg_types[i].getSize();
		int index;
		if (kind == 185)
			index = cp.addInterfaceMethodref(class_name, name, signature);
		else
			index = cp.addMethodref(class_name, name, signature);
		switch (kind) {
		case 183:
			return new INVOKESPECIAL(index);
		case 182:
			return new INVOKEVIRTUAL(index);
		case 184:
			return new INVOKESTATIC(index);
		case 185:
			return new INVOKEINTERFACE(index, nargs + 1);
		default:
			throw new RuntimeException(
					new StringBuilder().append("Oops: Unknown invoke kind:").append(kind).toString());
		}
	}

	public InstructionList createPrintln(String s) {
		final InstructionList il = new InstructionList();
		final int out = cp.addFieldref("java.lang.System", "out", "Ljava/io/PrintStream;");
		final int println = cp.addMethodref("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
		il.append(new GETSTATIC(out));
		il.append(new PUSH(cp, s));
		il.append(new INVOKEVIRTUAL(println));
		return il;
	}

	public Instruction createConstant(Object value) {
		PUSH push;
		if (value instanceof Number)
			push = new PUSH(cp, (Number) value);
		else if (value instanceof String)
			push = new PUSH(cp, (String) value);
		else if (value instanceof Boolean)
			push = new PUSH(cp, (Boolean) value);
		else if (value instanceof Character)
			push = new PUSH(cp, (Character) value);
		else
			throw new ClassGenException(
					new StringBuilder().append("Illegal type: ").append(value.getClass()).toString());
		return push.getInstruction();
	}

	private InvokeInstruction createInvoke(MethodObject m, short kind) {
		return createInvoke(m.class_name, m.name, m.result_type, m.arg_types, kind);
	}

	private static final boolean isString(Type type) {
		return type instanceof ObjectType && ((ObjectType) type).getClassName().equals("java.lang.String");
	}

	public Instruction createAppend(Type type) {
		final byte t = type.getType();
		if (isString(type))
			return createInvoke(append_mos[0], (short) 182);
		switch (t) {
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
			return createInvoke(append_mos[t], (short) 182);
		case 13:
		case 14:
			return createInvoke(append_mos[1], (short) 182);
		default:
			throw new RuntimeException(
					new StringBuilder().append("Oops: No append for this type? ").append(type).toString());
		}
	}

	public FieldInstruction createFieldAccess(String class_name, String name, Type type, short kind) {
		final String signature = type.getSignature();
		final int index = cp.addFieldref(class_name, name, signature);
		switch (kind) {
		case 180:
			return new GETFIELD(index);
		case 181:
			return new PUTFIELD(index);
		case 178:
			return new GETSTATIC(index);
		case 179:
			return new PUTSTATIC(index);
		default:
			throw new RuntimeException(
					new StringBuilder().append("Oops: Unknown getfield kind:").append(kind).toString());
		}
	}

	public static Instruction createThis() {
		return new ALOAD(0);
	}

	public static ReturnInstruction createReturn(Type type) {
		switch (type.getType()) {
		case 13:
		case 14:
			return ARETURN;
		case 4:
		case 5:
		case 8:
		case 9:
		case 10:
			return IRETURN;
		case 6:
			return FRETURN;
		case 7:
			return DRETURN;
		case 11:
			return LRETURN;
		case 12:
			return RETURN;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type: ").append(type).toString());
		}
	}

	private static final ArithmeticInstruction createBinaryIntOp(char first, String op) {
		switch (first) {
		case '-':
			return ISUB;
		case '+':
			return IADD;
		case '%':
			return IREM;
		case '*':
			return IMUL;
		case '/':
			return IDIV;
		case '&':
			return IAND;
		case '|':
			return IOR;
		case '^':
			return IXOR;
		case '<':
			return ISHL;
		case '>':
			return op.equals(">>>") ? IUSHR : ISHR;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid operand ").append(op).toString());
		}
	}

	private static final ArithmeticInstruction createBinaryLongOp(char first, String op) {
		switch (first) {
		case '-':
			return LSUB;
		case '+':
			return LADD;
		case '%':
			return LREM;
		case '*':
			return LMUL;
		case '/':
			return LDIV;
		case '&':
			return LAND;
		case '|':
			return LOR;
		case '^':
			return LXOR;
		case '<':
			return LSHL;
		case '>':
			return op.equals(">>>") ? LUSHR : LSHR;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid operand ").append(op).toString());
		}
	}

	private static final ArithmeticInstruction createBinaryFloatOp(char op) {
		switch (op) {
		case '-':
			return FSUB;
		case '+':
			return FADD;
		case '*':
			return FMUL;
		case '/':
			return FDIV;
		case '%':
			return FREM;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid operand ").append(op).toString());
		}
	}

	private static final ArithmeticInstruction createBinaryDoubleOp(char op) {
		switch (op) {
		case '-':
			return DSUB;
		case '+':
			return DADD;
		case '*':
			return DMUL;
		case '/':
			return DDIV;
		case '%':
			return DREM;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid operand ").append(op).toString());
		}
	}

	public static ArithmeticInstruction createBinaryOperation(String op, Type type) {
		final char first = op.toCharArray()[0];
		switch (type.getType()) {
		case 5:
		case 8:
		case 9:
		case 10:
			return createBinaryIntOp(first, op);
		case 11:
			return createBinaryLongOp(first, op);
		case 6:
			return createBinaryFloatOp(first);
		case 7:
			return createBinaryDoubleOp(first);
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type ").append(type).toString());
		}
	}

	public static StackInstruction createPop(int size) {
		return size == 2 ? POP2 : POP;
	}

	public static StackInstruction createDup(int size) {
		return size == 2 ? DUP2 : DUP;
	}

	public static StackInstruction createDup_2(int size) {
		return size == 2 ? DUP2_X2 : DUP_X2;
	}

	public static StackInstruction createDup_1(int size) {
		return size == 2 ? DUP2_X1 : DUP_X1;
	}

	public static LocalVariableInstruction createStore(Type type, int index) {
		switch (type.getType()) {
		case 4:
		case 5:
		case 8:
		case 9:
		case 10:
			return new ISTORE(index);
		case 6:
			return new FSTORE(index);
		case 7:
			return new DSTORE(index);
		case 11:
			return new LSTORE(index);
		case 13:
		case 14:
			return new ASTORE(index);
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type ").append(type).toString());
		}
	}

	public static LocalVariableInstruction createLoad(Type type, int index) {
		switch (type.getType()) {
		case 4:
		case 5:
		case 8:
		case 9:
		case 10:
			return new ILOAD(index);
		case 6:
			return new FLOAD(index);
		case 7:
			return new DLOAD(index);
		case 11:
			return new LLOAD(index);
		case 13:
		case 14:
			return new ALOAD(index);
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type ").append(type).toString());
		}
	}

	public static ArrayInstruction createArrayLoad(Type type) {
		switch (type.getType()) {
		case 4:
		case 8:
			return BALOAD;
		case 5:
			return CALOAD;
		case 9:
			return SALOAD;
		case 10:
			return IALOAD;
		case 6:
			return FALOAD;
		case 7:
			return DALOAD;
		case 11:
			return LALOAD;
		case 13:
		case 14:
			return AALOAD;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type ").append(type).toString());
		}
	}

	public static ArrayInstruction createArrayStore(Type type) {
		switch (type.getType()) {
		case 4:
		case 8:
			return BASTORE;
		case 5:
			return CASTORE;
		case 9:
			return SASTORE;
		case 10:
			return IASTORE;
		case 6:
			return FASTORE;
		case 7:
			return DASTORE;
		case 11:
			return LASTORE;
		case 13:
		case 14:
			return AASTORE;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type ").append(type).toString());
		}
	}

	public Instruction createCast(Type src_type, Type dest_type) {
		if (src_type instanceof BasicType && dest_type instanceof BasicType) {
			final byte dest = dest_type.getType();
			byte src = src_type.getType();
			if (dest == 11 && (src == 5 || src == 8 || src == 9))
				src = (byte) 10;
			final String name = new StringBuilder().append("org.apache.bcel.generic.").append(short_names[src - 5])
					.append("2").append(short_names[dest - 5]).toString();
			Instruction i = null;
			try {
				i = (Instruction) Class.forName(name).newInstance();
			} catch (final Exception e) {
				throw new RuntimeException(
						new StringBuilder().append("Could not find instruction: ").append(name).toString(), e);
			}
			return i;
		}
		if (src_type instanceof ReferenceType && dest_type instanceof ReferenceType) {
			if (dest_type instanceof ArrayType)
				return new CHECKCAST(cp.addArrayClass((ArrayType) dest_type));
			return new CHECKCAST(cp.addClass(((ObjectType) dest_type).getClassName()));
		}
		throw new RuntimeException(new StringBuilder().append("Can not cast ").append(src_type).append(" to ")
				.append(dest_type).toString());
	}

	public GETFIELD createGetField(String class_name, String name, Type t) {
		return new GETFIELD(cp.addFieldref(class_name, name, t.getSignature()));
	}

	public GETSTATIC createGetStatic(String class_name, String name, Type t) {
		return new GETSTATIC(cp.addFieldref(class_name, name, t.getSignature()));
	}

	public PUTFIELD createPutField(String class_name, String name, Type t) {
		return new PUTFIELD(cp.addFieldref(class_name, name, t.getSignature()));
	}

	public PUTSTATIC createPutStatic(String class_name, String name, Type t) {
		return new PUTSTATIC(cp.addFieldref(class_name, name, t.getSignature()));
	}

	public CHECKCAST createCheckCast(ReferenceType t) {
		if (t instanceof ArrayType)
			return new CHECKCAST(cp.addArrayClass((ArrayType) t));
		return new CHECKCAST(cp.addClass((ObjectType) t));
	}

	public INSTANCEOF createInstanceOf(ReferenceType t) {
		if (t instanceof ArrayType)
			return new INSTANCEOF(cp.addArrayClass((ArrayType) t));
		return new INSTANCEOF(cp.addClass((ObjectType) t));
	}

	public NEW createNew(ObjectType t) {
		return new NEW(cp.addClass(t));
	}

	public NEW createNew(String s) {
		return createNew(new ObjectType(s));
	}

	public Instruction createNewArray(Type t, short dim) {
		if (dim == 1) {
			if (t instanceof ObjectType)
				return new ANEWARRAY(cp.addClass((ObjectType) t));
			if (t instanceof ArrayType)
				return new ANEWARRAY(cp.addArrayClass((ArrayType) t));
			return new NEWARRAY(((BasicType) t).getType());
		}
		ArrayType at;
		if (t instanceof ArrayType)
			at = (ArrayType) t;
		else
			at = new ArrayType(t, dim);
		return new MULTIANEWARRAY(cp.addArrayClass(at), dim);
	}

	public static Instruction createNull(Type type) {
		switch (type.getType()) {
		case 13:
		case 14:
			return ACONST_NULL;
		case 4:
		case 5:
		case 8:
		case 9:
		case 10:
			return ICONST_0;
		case 6:
			return FCONST_0;
		case 7:
			return DCONST_0;
		case 11:
			return LCONST_0;
		case 12:
			return NOP;
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid type: ").append(type).toString());
		}
	}

	public static BranchInstruction createBranchInstruction(short opcode, InstructionHandle target) {
		switch (opcode) {
		case 153:
			return new IFEQ(target);
		case 154:
			return new IFNE(target);
		case 155:
			return new IFLT(target);
		case 156:
			return new IFGE(target);
		case 157:
			return new IFGT(target);
		case 158:
			return new IFLE(target);
		case 159:
			return new IF_ICMPEQ(target);
		case 160:
			return new IF_ICMPNE(target);
		case 161:
			return new IF_ICMPLT(target);
		case 162:
			return new IF_ICMPGE(target);
		case 163:
			return new IF_ICMPGT(target);
		case 164:
			return new IF_ICMPLE(target);
		case 165:
			return new IF_ACMPEQ(target);
		case 166:
			return new IF_ACMPNE(target);
		case 167:
			return new GOTO(target);
		case 168:
			return new JSR(target);
		case 198:
			return new IFNULL(target);
		case 199:
			return new IFNONNULL(target);
		case 200:
			return new GOTO_W(target);
		case 201:
			return new JSR_W(target);
		default:
			throw new RuntimeException(new StringBuilder().append("Invalid opcode: ").append(opcode).toString());
		}
	}

	public void setClassGen(ClassGen c) {
		cg = c;
	}

	public ClassGen getClassGen() {
		return cg;
	}

	public void setConstantPool(ConstantPoolGen c) {
		cp = c;
	}

	public ConstantPoolGen getConstantPool() {
		return cp;
	}
}
