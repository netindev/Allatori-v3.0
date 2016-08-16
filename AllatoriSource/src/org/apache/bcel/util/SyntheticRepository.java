package org.apache.bcel.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

public class SyntheticRepository implements Repository {

	private static final String DEFAULT_PATH = ClassPath.getClassPath();
	private static Map _instances = new HashMap();
	private ClassPath _path = null;
	private Map _loadedClasses = new HashMap();

	private SyntheticRepository(ClassPath path) {
		_path = path;
	}

	public static SyntheticRepository getInstance() {
		return getInstance(ClassPath.SYSTEM_CLASS_PATH);
	}

	public static SyntheticRepository getInstance(ClassPath classPath) {
		SyntheticRepository rep = (SyntheticRepository) _instances.get(classPath);
		if (rep == null) {
			rep = new SyntheticRepository(classPath);
			_instances.put(classPath, rep);
		}
		return rep;
	}

	public void storeClass(JavaClass clazz) {
		_loadedClasses.put(clazz.getClassName(), new SoftReference(clazz));
		clazz.setRepository(this);
	}

	public void removeClass(JavaClass clazz) {
		_loadedClasses.remove(clazz.getClassName());
	}

	public JavaClass findClass(String className) {
		SoftReference ref = (SoftReference) _loadedClasses.get(className);
		if (ref == null) {
			return null;
		}
		return (JavaClass) ref.get();
	}

	public JavaClass loadClass(String className) throws ClassNotFoundException {
		if (className == null || className.equals("")) {
			throw new IllegalArgumentException("Invalid class name " + className);
		}
		className = className.replace('/', '.');
		JavaClass clazz = findClass(className);
		if (clazz != null) {
			return clazz;
		}
		try {
			return loadClass(_path.getInputStream(className), className);
		} catch (IOException e) {
			throw new ClassNotFoundException("Exception while looking for class " + className + ": " + e.toString());
		}
	}

	public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
		String className = clazz.getName();
		JavaClass repositoryClass = findClass(className);
		if (repositoryClass != null) {
			return repositoryClass;
		}
		String name = className;
		int i = name.lastIndexOf('.');
		if (i > 0) {
			name = name.substring(i + 1);
		}
		return loadClass(clazz.getResourceAsStream(name + ".class"), className);
	}

	private JavaClass loadClass(InputStream is, String className) throws ClassNotFoundException {
		try {
			if (is != null) {
				ClassParser parser = new ClassParser(is, className);
				JavaClass clazz = parser.parse();
				storeClass(clazz);
				return clazz;
			}
		} catch (IOException e) {
			throw new ClassNotFoundException("Exception while looking for class " + className + ": " + e.toString());
		}
		throw new ClassNotFoundException("SyntheticRepository could not load " + className);
	}

	public ClassPath getClassPath() {
		return _path;
	}

	public void clear() {
		_loadedClasses.clear();
	}
}
