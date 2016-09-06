package org.apache.bcel.generic;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.Utility;

public abstract class Type implements Serializable {
	private static final long serialVersionUID = -1985077286871826895L;
	protected byte type;
	protected String signature;
	public static final BasicType VOID = new BasicType((byte) 12);
	public static final BasicType BOOLEAN = new BasicType((byte) 4);
	public static final BasicType INT = new BasicType((byte) 10);
	public static final BasicType SHORT = new BasicType((byte) 9);
	public static final BasicType BYTE = new BasicType((byte) 8);
	public static final BasicType LONG = new BasicType((byte) 11);
	public static final BasicType DOUBLE = new BasicType((byte) 7);
	public static final BasicType FLOAT = new BasicType((byte) 6);
	public static final BasicType CHAR = new BasicType((byte) 5);
	public static final ObjectType OBJECT = new ObjectType("java.lang.Object");
	public static final ObjectType CLASS = new ObjectType("java.lang.Class");
	public static final ObjectType STRING = new ObjectType("java.lang.String");
	public static final ObjectType STRINGBUFFER = new ObjectType("java.lang.StringBuffer");
	public static final ObjectType THROWABLE = new ObjectType("java.lang.Throwable");
	public static final Type[] NO_ARGS = new Type[0];

	public static final ReferenceType NULL = new ReferenceType() {
		private static final long serialVersionUID = 4526765862386946282L;
	};

	public static final Type UNKNOWN = new Type((byte) 15, "<unknown object>") {
		private static final long serialVersionUID = 1321113605813486066L;
	};

	private static ThreadLocal<Object> consumed_chars = new ThreadLocal<Object>() {
		@Override
		protected Object initialValue() {
			return new Integer(0);
		}
	};

	protected Type(byte t, String s) {
		type = t;
		signature = s;
	}

	@Override
	public int hashCode() {
		return type ^ signature.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Type) {
			final Type t = (Type) o;
			return type == t.type && signature.equals(t.signature);
		}
		return false;
	}

	public String getSignature() {
		return signature;
	}

	public byte getType() {
		return type;
	}

	public int getSize() {
		switch (type) {
		case 7:
		case 11:
			return 2;
		case 12:
			return 0;
		default:
			return 1;
		}
	}

	@Override
	public String toString() {
		return (equals(NULL) || type >= 15 ? signature : Utility.signatureToString(signature, false));
	}

	public static String getMethodSignature(Type return_type, Type[] arg_types) {
		final StringBuilder buf = new StringBuilder("(");
		if (arg_types != null) {
			final Type[] arr$ = arg_types;
			final int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++) {
				final Type arg_type = arr$[i$];
				buf.append(arg_type.getSignature());
			}
		}
		buf.append(')');
		buf.append(return_type.getSignature());
		return buf.toString();
	}

	private static int unwrap(ThreadLocal<Object> consumed_chars2) {
		return ((Integer) consumed_chars2.get()).intValue();
	}

	private static void wrap(ThreadLocal<Object> consumed_chars2, int value) {
		consumed_chars2.set(Integer.valueOf(value));
	}

	public static final Type getType(String signature) throws StringIndexOutOfBoundsException {
		final byte type = Utility.typeOfSignature(signature);
		if (type <= 12) {
			wrap(consumed_chars, 1);
			return BasicType.getType(type);
		}
		if (type == 13) {
			int dim = 0;
			do
				dim++;
			while (signature.charAt(dim) == '[');
			final Type t = getType(signature.substring(dim));
			final int _temp = unwrap(consumed_chars) + dim;
			wrap(consumed_chars, _temp);
			return new ArrayType(t, dim);
		}
		final int index = signature.indexOf(';');
		if (index < 0)
			throw new ClassFormatException(
					new StringBuilder().append("Invalid signature: ").append(signature).toString());
		wrap(consumed_chars, index + 1);
		return new ObjectType(signature.substring(1, index).replace('/', '.'));
	}

	public static Type getReturnType(String signature) {
		Type type;
		try {
			final int index = signature.lastIndexOf(')') + 1;
			type = getType(signature.substring(index));
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		return type;
	}

	public static Type[] getArgumentTypes(String signature) {
		final ArrayList<Type> vec = new ArrayList<Type>();
		try {
			if (signature.charAt(0) != '(')
				throw new ClassFormatException(
						new StringBuilder().append("Invalid method signature: ").append(signature).toString());
			for (int index = 1; signature.charAt(index) != ')'; index += unwrap(consumed_chars))
				vec.add(getType(signature.substring(index)));
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		final Type[] types = new Type[vec.size()];
		vec.toArray(types);
		return types;
	}

	public static Type getType(Class<?> cl) {
		if (cl == null)
			throw new IllegalArgumentException("Class must not be null");
		if (cl.isArray())
			return getType(cl.getName());
		if (cl.isPrimitive()) {
			if (cl == Integer.TYPE)
				return INT;
			if (cl == Void.TYPE)
				return VOID;
			if (cl == Double.TYPE)
				return DOUBLE;
			if (cl == Float.TYPE)
				return FLOAT;
			if (cl == Boolean.TYPE)
				return BOOLEAN;
			if (cl == Byte.TYPE)
				return BYTE;
			if (cl == Short.TYPE)
				return SHORT;
			if (cl == Byte.TYPE)
				return BYTE;
			if (cl == Long.TYPE)
				return LONG;
			if (cl == Character.TYPE)
				return CHAR;
			throw new IllegalStateException(
					new StringBuilder().append("Ooops, what primitive type is ").append(cl).toString());
		}
		return new ObjectType(cl.getName());
	}

	public static Type[] getTypes(Class<?>[] classes) {
		final Type[] ret = new Type[classes.length];
		for (int i = 0; i < ret.length; i++)
			ret[i] = getType(classes[i]);
		return ret;
	}

	public static String getSignature(Method meth) {
		final StringBuilder sb = new StringBuilder("(");
		final Class<?>[] params = meth.getParameterTypes();
		for (int j = 0; j < params.length; j++)
			sb.append(getType(params[j]).getSignature());
		sb.append(")");
		sb.append(getType(meth.getReturnType()).getSignature());
		return sb.toString();
	}

	static int size(int coded) {
		return coded & 0x3;
	}

	static int consumed(int coded) {
		return coded >> 2;
	}

	static int encode(int size, int consumed) {
		return consumed << 2 | size;
	}

	static int getArgumentTypesSize(String signature) {
		int res = 0;
		try {
			if (signature.charAt(0) != '(')
				throw new ClassFormatException(
						new StringBuilder().append("Invalid method signature: ").append(signature).toString());
			int coded;
			for (int index = 1; signature.charAt(index) != ')'; index += consumed(coded)) {
				coded = getTypeSize(signature.substring(index));
				res += size(coded);
			}
		} catch (final StringIndexOutOfBoundsException e) {
			throw new ClassFormatException(
					new StringBuilder().append("Invalid method signature: ").append(signature).toString(), e);
		}
		return res;
	}

	static final int getTypeSize(String signature) throws StringIndexOutOfBoundsException {
		final byte type = Utility.typeOfSignature(signature);
		if (type <= 12)
			return encode(BasicType.getType(type).getSize(), 1);
		if (type == 13) {
			int dim = 0;
			do
				dim++;
			while (signature.charAt(dim) == '[');
			final int consumed = consumed(getTypeSize(signature.substring(dim)));
			return encode(1, dim + consumed);
		}
		final int index = signature.indexOf(';');
		if (index < 0)
			throw new ClassFormatException(
					new StringBuilder().append("Invalid signature: ").append(signature).toString());
		return encode(1, index + 1);
	}

	static int getReturnTypeSize(String signature) {
		final int index = signature.lastIndexOf(')') + 1;
		return size(getTypeSize(signature.substring(index)));
	}
}
