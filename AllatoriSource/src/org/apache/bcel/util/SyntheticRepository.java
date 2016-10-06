package org.apache.bcel.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

public class SyntheticRepository implements Repository {

	private static final long serialVersionUID = 1L;
	private static Map<ClassPath, SyntheticRepository> _instances = new HashMap<ClassPath, SyntheticRepository>();
	private ClassPath _path = null;
	private final Map<String, SoftReference<JavaClass>> _loadedClasses = new HashMap<String, SoftReference<JavaClass>>();

	private SyntheticRepository(ClassPath path) {
		_path = path;
	}

	public static SyntheticRepository getInstance() {
		return getInstance(ClassPath.SYSTEM_CLASS_PATH);
	}

	public static SyntheticRepository getInstance(ClassPath classPath) {
		SyntheticRepository rep = _instances.get(classPath);
		if (rep == null) {
			rep = new SyntheticRepository(classPath);
			_instances.put(classPath, rep);
		}
		return rep;
	}

	@Override
	public void storeClass(JavaClass clazz) {
		_loadedClasses.put(clazz.getClassName(), new SoftReference<JavaClass>(clazz));
		clazz.setRepository(this);
	}

	@Override
	public void removeClass(JavaClass clazz) {
		_loadedClasses.remove(clazz.getClassName());
	}

	@Override
	public JavaClass findClass(String className) {
		final SoftReference<?> ref = _loadedClasses.get(className);
		if (ref == null) {
			return null;
		}
		return (JavaClass) ref.get();
	}

	@Override
	public JavaClass loadClass(String className) throws ClassNotFoundException {
		if (className == null || className.equals("")) {
			throw new IllegalArgumentException("Invalid class name " + className);
		}
		className = className.replace('/', '.');
		final JavaClass clazz = findClass(className);
		if (clazz != null) {
			return clazz;
		}
		try {
			return loadClass(_path.getInputStream(className), className);
		} catch (final IOException e) {
			throw new ClassNotFoundException("Exception while looking for class " + className + ": " + e.toString());
		}
	}

	@Override
	public JavaClass loadClass(Class<?> clazz) throws ClassNotFoundException {
		final String className = clazz.getName();
		final JavaClass repositoryClass = findClass(className);
		if (repositoryClass != null) {
			return repositoryClass;
		}
		String name = className;
		final int i = name.lastIndexOf('.');
		if (i > 0) {
			name = name.substring(i + 1);
		}
		return loadClass(clazz.getResourceAsStream(name + ".class"), className);
	}

	private JavaClass loadClass(InputStream is, String className) throws ClassNotFoundException {
		try {
			if (is != null) {
				final ClassParser parser = new ClassParser(is, className);
				final JavaClass clazz = parser.parse();
				storeClass(clazz);
				return clazz;
			}
		} catch (final IOException e) {
			throw new ClassNotFoundException("Exception while looking for class " + className + ": " + e.toString());
		}
		throw new ClassNotFoundException("SyntheticRepository could not load " + className);
	}

	@Override
	public ClassPath getClassPath() {
		return _path;
	}

	@Override
	public void clear() {
		_loadedClasses.clear();
	}
}
