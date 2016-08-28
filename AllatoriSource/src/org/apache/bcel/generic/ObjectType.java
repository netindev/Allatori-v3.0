package org.apache.bcel.generic;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

public class ObjectType extends ReferenceType {
	private static final long serialVersionUID = -2819379966444533294L;
	private final String class_name;

	public ObjectType(String class_name) {
		super((byte) 14, new StringBuilder().append("L").append(class_name.replace('.', '/')).append(";").toString());
		this.class_name = class_name.replace('/', '.');
	}

	public String getClassName() {
		return class_name;
	}

	@Override
	public int hashCode() {
		return class_name.hashCode();
	}

	@Override
	public boolean equals(Object type) {
		return (type instanceof ObjectType ? ((ObjectType) type).class_name.equals(class_name) : false);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public boolean referencesClass() {
		boolean bool;
		try {
			final JavaClass jc = Repository.lookupClass(class_name);
			bool = jc.isClass();
		} catch (final ClassNotFoundException e) {
			return false;
		}
		return bool;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public boolean referencesInterface() {
		boolean bool;
		try {
			final JavaClass jc = Repository.lookupClass(class_name);
			bool = !jc.isClass();
		} catch (final ClassNotFoundException e) {
			return false;
		}
		return bool;
	}

	public boolean referencesClassExact() throws ClassNotFoundException {
		final JavaClass jc = Repository.lookupClass(class_name);
		return jc.isClass();
	}

	public boolean referencesInterfaceExact() throws ClassNotFoundException {
		final JavaClass jc = Repository.lookupClass(class_name);
		return !jc.isClass();
	}

	public boolean subclassOf(ObjectType superclass) throws ClassNotFoundException {
		if (referencesInterface() || superclass.referencesInterface())
			return false;
		return Repository.instanceOf(class_name, superclass.class_name);
	}

	public boolean accessibleTo(ObjectType accessor) throws ClassNotFoundException {
		final JavaClass jc = Repository.lookupClass(class_name);
		if (jc.isPublic())
			return true;
		final JavaClass acc = Repository.lookupClass(accessor.class_name);
		return acc.getPackageName().equals(jc.getPackageName());
	}
}
