package org.apache.bcel.generic;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

public class ObjectType extends ReferenceType {
	private static final long serialVersionUID = -2819379966444533294L;
	private String class_name;

	public ObjectType(String class_name) {
		super((byte) 14, new StringBuilder().append("L").append(class_name.replace('.', '/')).append(";").toString());
		this.class_name = class_name.replace('/', '.');
	}

	public String getClassName() {
		return class_name;
	}

	public int hashCode() {
		return class_name.hashCode();
	}

	public boolean equals(Object type) {
		return (type instanceof ObjectType ? ((ObjectType) type).class_name.equals(class_name) : false);
	}

	/**
	 * @deprecated
	 */
	public boolean referencesClass() {
		boolean bool;
		try {
			JavaClass jc = Repository.lookupClass(class_name);
			bool = jc.isClass();
		} catch (ClassNotFoundException e) {
			return false;
		}
		return bool;
	}

	/**
	 * @deprecated
	 */
	public boolean referencesInterface() {
		boolean bool;
		try {
			JavaClass jc = Repository.lookupClass(class_name);
			bool = !jc.isClass();
		} catch (ClassNotFoundException e) {
			return false;
		}
		return bool;
	}

	public boolean referencesClassExact() throws ClassNotFoundException {
		JavaClass jc = Repository.lookupClass(class_name);
		return jc.isClass();
	}

	public boolean referencesInterfaceExact() throws ClassNotFoundException {
		JavaClass jc = Repository.lookupClass(class_name);
		return !jc.isClass();
	}

	public boolean subclassOf(ObjectType superclass) throws ClassNotFoundException {
		if (referencesInterface() || superclass.referencesInterface())
			return false;
		return Repository.instanceOf(class_name, superclass.class_name);
	}

	public boolean accessibleTo(ObjectType accessor) throws ClassNotFoundException {
		JavaClass jc = Repository.lookupClass(class_name);
		if (jc.isPublic())
			return true;
		JavaClass acc = Repository.lookupClass(accessor.class_name);
		return acc.getPackageName().equals(jc.getPackageName());
	}
}
