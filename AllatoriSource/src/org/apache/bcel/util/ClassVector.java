package org.apache.bcel.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.JavaClass;

public class ClassVector implements Serializable {
	private static final long serialVersionUID = 5600397075672780806L;
	protected List vec = new ArrayList();

	public void addElement(JavaClass clazz) {
		vec.add(clazz);
	}

	public JavaClass elementAt(int index) {
		return (JavaClass) vec.get(index);
	}

	public void removeElementAt(int index) {
		vec.remove(index);
	}

	public JavaClass[] toArray() {
		JavaClass[] classes = new JavaClass[vec.size()];
		vec.toArray(classes);
		return classes;
	}
}
