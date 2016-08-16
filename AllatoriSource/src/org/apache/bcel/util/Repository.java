package org.apache.bcel.util;

import java.io.Serializable;

import org.apache.bcel.classfile.JavaClass;

public interface Repository extends Serializable {
	public void storeClass(JavaClass javaclass);

	public void removeClass(JavaClass javaclass);

	public JavaClass findClass(String string);

	public JavaClass loadClass(String string) throws ClassNotFoundException;

	public JavaClass loadClass(Class var_class) throws ClassNotFoundException;

	public void clear();

	public ClassPath getClassPath();
}
