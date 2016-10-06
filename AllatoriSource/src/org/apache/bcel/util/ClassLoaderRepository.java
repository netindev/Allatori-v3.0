package org.apache.bcel.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

public class ClassLoaderRepository implements Repository {
	private static final long serialVersionUID = -1052781833503868187L;
	private final java.lang.ClassLoader loader;
	private final Map<String, JavaClass> loadedClasses = new HashMap<String, JavaClass>();

	public ClassLoaderRepository(java.lang.ClassLoader loader) {
		this.loader = loader;
	}

	@Override
	public void storeClass(JavaClass clazz) {
		loadedClasses.put(clazz.getClassName(), clazz);
		clazz.setRepository(this);
	}

	@Override
	public void removeClass(JavaClass clazz) {
		loadedClasses.remove(clazz.getClassName());
	}

	@Override
	public JavaClass findClass(String className) {
		if (loadedClasses.containsKey(className))
			return loadedClasses.get(className);
		return null;
	}

	@Override
	public JavaClass loadClass(String className) throws ClassNotFoundException {
		final String classFile = className.replace('.', '/');
		JavaClass RC = findClass(className);
		if (RC != null)
			return RC;
		JavaClass javaclass;
		try {
			final InputStream is = loader
					.getResourceAsStream(new StringBuilder().append(classFile).append(".class").toString());
			if (is == null)
				throw new ClassNotFoundException(
						new StringBuilder().append(className).append(" not found.").toString());
			final ClassParser parser = new ClassParser(is, className);
			RC = parser.parse();
			storeClass(RC);
			javaclass = RC;
		} catch (final IOException e) {
			throw new ClassNotFoundException(
					new StringBuilder().append(className).append(" not found: ").append(e.toString()).toString(), e);
		}
		return javaclass;
	}

	@Override
	public JavaClass loadClass(Class<?> clazz) throws ClassNotFoundException {
		return loadClass(clazz.getName());
	}

	@Override
	public void clear() {
		loadedClasses.clear();
	}

	@Override
	public ClassPath getClassPath() {
		return null;
	}
}
