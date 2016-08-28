package org.apache.bcel.generic;

import org.apache.bcel.Constants;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

public abstract class ReferenceType extends Type {
	private static final long serialVersionUID = -1434716548829506031L;

	protected ReferenceType(byte t, String s) {
		super(t, s);
	}

	ReferenceType() {
		super((byte) 14, "<null object>");
	}

	public boolean isCastableTo(Type t) throws ClassNotFoundException {
		if (equals(Type.NULL))
			return true;
		return isAssignmentCompatibleWith(t);
	}

	public boolean isAssignmentCompatibleWith(Type t) throws ClassNotFoundException {
		if (!(t instanceof ReferenceType))
			return false;
		final ReferenceType T = (ReferenceType) t;
		if (equals(Type.NULL))
			return true;
		if (this instanceof ObjectType && ((ObjectType) this).referencesClassExact()) {
			if (T instanceof ObjectType && ((ObjectType) T).referencesClassExact()) {
				if (equals(T))
					return true;
				if (Repository.instanceOf(((ObjectType) this).getClassName(), ((ObjectType) T).getClassName()))
					return true;
			}
			if (T instanceof ObjectType && ((ObjectType) T).referencesInterfaceExact()
					&& Repository.implementationOf(((ObjectType) this).getClassName(), ((ObjectType) T).getClassName()))
				return true;
		}
		if (this instanceof ObjectType && ((ObjectType) this).referencesInterfaceExact()) {
			if (T instanceof ObjectType && ((ObjectType) T).referencesClassExact() && T.equals(Type.OBJECT))
				return true;
			if (T instanceof ObjectType && ((ObjectType) T).referencesInterfaceExact()) {
				if (equals(T))
					return true;
				if (Repository.implementationOf(((ObjectType) this).getClassName(), ((ObjectType) T).getClassName()))
					return true;
			}
		}
		if (this instanceof ArrayType) {
			if (T instanceof ObjectType && ((ObjectType) T).referencesClassExact() && T.equals(Type.OBJECT))
				return true;
			if (T instanceof ArrayType) {
				final Type sc = ((ArrayType) this).getElementType();
				final Type tc = ((ArrayType) T).getElementType();
				if (sc instanceof BasicType && tc instanceof BasicType && sc.equals(tc))
					return true;
				if (tc instanceof ReferenceType && sc instanceof ReferenceType
						&& ((ReferenceType) sc).isAssignmentCompatibleWith(tc))
					return true;
			}
			if (T instanceof ObjectType && ((ObjectType) T).referencesInterfaceExact()) {
				for (int ii = 0; ii < Constants.INTERFACES_IMPLEMENTED_BY_ARRAYS.length; ii++) {
					if (T.equals(new ObjectType(Constants.INTERFACES_IMPLEMENTED_BY_ARRAYS[ii])))
						return true;
				}
			}
		}
		return false;
	}

	public ReferenceType getFirstCommonSuperclass(ReferenceType t) throws ClassNotFoundException {
		if (equals(Type.NULL))
			return t;
		if (t.equals(Type.NULL))
			return this;
		if (equals(t))
			return this;
		if (this instanceof ArrayType && t instanceof ArrayType) {
			final ArrayType arrType1 = (ArrayType) this;
			final ArrayType arrType2 = (ArrayType) t;
			if (arrType1.getDimensions() == arrType2.getDimensions() && arrType1.getBasicType() instanceof ObjectType
					&& arrType2.getBasicType() instanceof ObjectType)
				return new ArrayType(
						(((ObjectType) arrType1.getBasicType())
								.getFirstCommonSuperclass((ObjectType) arrType2.getBasicType())),
						arrType1.getDimensions());
		}
		if (this instanceof ArrayType || t instanceof ArrayType)
			return Type.OBJECT;
		if ((this instanceof ObjectType && ((ObjectType) this).referencesInterface())
				|| (t instanceof ObjectType && ((ObjectType) t).referencesInterface()))
			return Type.OBJECT;
		final ObjectType thiz = (ObjectType) this;
		final ObjectType other = (ObjectType) t;
		final JavaClass[] thiz_sups = Repository.getSuperClasses(thiz.getClassName());
		final JavaClass[] other_sups = Repository.getSuperClasses(other.getClassName());
		if (thiz_sups == null || other_sups == null)
			return null;
		final JavaClass[] this_sups = new JavaClass[thiz_sups.length + 1];
		final JavaClass[] t_sups = new JavaClass[other_sups.length + 1];
		System.arraycopy(thiz_sups, 0, this_sups, 1, thiz_sups.length);
		System.arraycopy(other_sups, 0, t_sups, 1, other_sups.length);
		this_sups[0] = Repository.lookupClass(thiz.getClassName());
		t_sups[0] = Repository.lookupClass(other.getClassName());
		for (int i = 0; i < t_sups.length; i++) {
			for (int j = 0; j < this_sups.length; j++) {
				if (this_sups[j].equals(t_sups[i]))
					return new ObjectType(this_sups[j].getClassName());
			}
		}
		return null;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public ReferenceType firstCommonSuperclass(ReferenceType t) throws ClassNotFoundException {
		if (equals(Type.NULL))
			return t;
		if (t.equals(Type.NULL))
			return this;
		if (equals(t))
			return this;
		if (this instanceof ArrayType || t instanceof ArrayType)
			return Type.OBJECT;
		if ((this instanceof ObjectType && ((ObjectType) this).referencesInterface())
				|| (t instanceof ObjectType && ((ObjectType) t).referencesInterface()))
			return Type.OBJECT;
		final ObjectType thiz = (ObjectType) this;
		final ObjectType other = (ObjectType) t;
		final JavaClass[] thiz_sups = Repository.getSuperClasses(thiz.getClassName());
		final JavaClass[] other_sups = Repository.getSuperClasses(other.getClassName());
		if (thiz_sups == null || other_sups == null)
			return null;
		final JavaClass[] this_sups = new JavaClass[thiz_sups.length + 1];
		final JavaClass[] t_sups = new JavaClass[other_sups.length + 1];
		System.arraycopy(thiz_sups, 0, this_sups, 1, thiz_sups.length);
		System.arraycopy(other_sups, 0, t_sups, 1, other_sups.length);
		this_sups[0] = Repository.lookupClass(thiz.getClassName());
		t_sups[0] = Repository.lookupClass(other.getClassName());
		for (int i = 0; i < t_sups.length; i++) {
			for (int j = 0; j < this_sups.length; j++) {
				if (this_sups[j].equals(t_sups[i]))
					return new ObjectType(this_sups[j].getClassName());
			}
		}
		return null;
	}
}
