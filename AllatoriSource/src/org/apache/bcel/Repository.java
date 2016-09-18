package org.apache.bcel;

import java.io.IOException;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;

public abstract class Repository {

	private static org.apache.bcel.util.Repository _repository = SyntheticRepository.getInstance();

	public static org.apache.bcel.util.Repository getRepository() {
		return _repository;
	}

	public static void setRepository(org.apache.bcel.util.Repository rep) {
		_repository = rep;
	}

	public static JavaClass lookupClass(String class_name) throws ClassNotFoundException {
		return _repository.loadClass(class_name);
	}
	
	public static JavaClass lookupClass(Class<?> clazz) throws ClassNotFoundException {
		return _repository.loadClass(clazz);
	}

	public static ClassPath.ClassFile lookupClassFile(String class_name) {
		try {
			final ClassPath path = _repository.getClassPath();
			if (path == null) {
				return null;
			}
			return path.getClassFile(class_name);
		} catch (final IOException e) {
			return null;
		}
	}

	public static void clearCache() {
		_repository.clear();
	}

	public static JavaClass addClass(JavaClass clazz) {
		final JavaClass old = _repository.findClass(clazz.getClassName());
		_repository.storeClass(clazz);
		return old;
	}

	public static void removeClass(String clazz) {
		_repository.removeClass(_repository.findClass(clazz));
	}

	public static void removeClass(JavaClass clazz) {
		_repository.removeClass(clazz);
	}

	public static JavaClass[] getSuperClasses(JavaClass clazz) throws ClassNotFoundException {
		return clazz.getSuperClasses();
	}

	public static JavaClass[] getSuperClasses(String class_name) throws ClassNotFoundException {
		final JavaClass jc = lookupClass(class_name);
		return getSuperClasses(jc);
	}

	public static JavaClass[] getInterfaces(JavaClass clazz) throws ClassNotFoundException {
		return clazz.getAllInterfaces();
	}

	public static JavaClass[] getInterfaces(String class_name) throws ClassNotFoundException {
		return getInterfaces(lookupClass(class_name));
	}

	public static boolean instanceOf(JavaClass clazz, JavaClass super_class) throws ClassNotFoundException {
		return clazz.instanceOf(super_class);
	}

	public static boolean instanceOf(String clazz, String super_class) throws ClassNotFoundException {
		return instanceOf(lookupClass(clazz), lookupClass(super_class));
	}

	public static boolean instanceOf(JavaClass clazz, String super_class) throws ClassNotFoundException {
		return instanceOf(clazz, lookupClass(super_class));
	}

	public static boolean instanceOf(String clazz, JavaClass super_class) throws ClassNotFoundException {
		return instanceOf(lookupClass(clazz), super_class);
	}

	public static boolean implementationOf(JavaClass clazz, JavaClass inter) throws ClassNotFoundException {
		return clazz.implementationOf(inter);
	}

	public static boolean implementationOf(String clazz, String inter) throws ClassNotFoundException {
		return implementationOf(lookupClass(clazz), lookupClass(inter));
	}

	public static boolean implementationOf(JavaClass clazz, String inter) throws ClassNotFoundException {
		return implementationOf(clazz, lookupClass(inter));
	}

	public static boolean implementationOf(String clazz, JavaClass inter) throws ClassNotFoundException {
		return implementationOf(lookupClass(clazz), inter);
	}
}
