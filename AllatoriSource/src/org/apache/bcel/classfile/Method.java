package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.bcel.generic.Type;
import org.apache.bcel.util.BCELComparator;

public final class Method extends FieldOrMethod {
	private static final long serialVersionUID = -2013983967283787941L;
	private static BCELComparator _cmp = new BCELComparator() {
		@Override
		public boolean equals(Object o1, Object o2) {
			final Method THIS = (Method) o1;
			final Method THAT = (Method) o2;
			return (THIS.getName().equals(THAT.getName()) && THIS.getSignature().equals(THAT.getSignature()));
		}

		@Override
		public int hashCode(Object o) {
			final Method THIS = (Method) o;
			return THIS.getSignature().hashCode() ^ THIS.getName().hashCode();
		}
	};

	public Method() {
		/* empty */
	}

	public Method(Method c) {
		super(c);
	}

	Method(DataInputStream file, ConstantPool constant_pool) throws IOException, ClassFormatException {
		super(file, constant_pool);
	}

	public Method(int access_flags, int name_index, int signature_index, Attribute[] attributes,
			ConstantPool constant_pool) {
		super(access_flags, name_index, signature_index, attributes, constant_pool);
	}

	@Override
	public void accept(Visitor v) {
		v.visitMethod(this);
	}

	public final Code getCode() {
		for (int i = 0; i < attributes_count; i++) {
			if (attributes[i] instanceof Code)
				return (Code) attributes[i];
		}
		return null;
	}

	public final ExceptionTable getExceptionTable() {
		for (int i = 0; i < attributes_count; i++) {
			if (attributes[i] instanceof ExceptionTable)
				return (ExceptionTable) attributes[i];
		}
		return null;
	}

	public final LocalVariableTable getLocalVariableTable() {
		final Code code = getCode();
		if (code == null)
			return null;
		return code.getLocalVariableTable();
	}

	public final LineNumberTable getLineNumberTable() {
		final Code code = getCode();
		if (code == null)
			return null;
		return code.getLineNumberTable();
	}

	@Override
	public final String toString() {
		final String access = Utility.accessToString(access_flags);
		ConstantUtf8 c = ((ConstantUtf8) constant_pool.getConstant(signature_index, (byte) 1));
		String signature = c.getBytes();
		c = (ConstantUtf8) constant_pool.getConstant(name_index, (byte) 1);
		final String name = c.getBytes();
		signature = Utility.methodSignatureToString(signature, name, access, true, getLocalVariableTable());
		final StringBuilder buf = new StringBuilder(signature);
		for (int i = 0; i < attributes_count; i++) {
			final Attribute a = attributes[i];
			if (!(a instanceof Code) && !(a instanceof ExceptionTable))
				buf.append(" [").append(a.toString()).append("]");
		}
		final ExceptionTable e = getExceptionTable();
		if (e != null) {
			final String str = e.toString();
			if (!str.equals(""))
				buf.append("\n\t\tthrows ").append(str);
		}
		return buf.toString();
	}

	public final Method copy(ConstantPool _constant_pool) {
		return (Method) copy_(_constant_pool);
	}

	public Type getReturnType() {
		return Type.getReturnType(getSignature());
	}

	public Type[] getArgumentTypes() {
		return Type.getArgumentTypes(getSignature());
	}

	public static BCELComparator getComparator() {
		return _cmp;
	}

	public static void setComparator(BCELComparator comparator) {
		_cmp = comparator;
	}

	@Override
	public boolean equals(Object obj) {
		return _cmp.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return _cmp.hashCode(this);
	}
}
