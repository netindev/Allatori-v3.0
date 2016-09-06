package com.allatori;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class URLClassLoaderImpl extends URLClassLoader {
	
	/* OK */

	private ClassStorage classes;
	public static Class<?> classLoaderClass;

	@Override
	public Class<?> findClass(String str) throws ClassNotFoundException {
		try {
			try {
				if (this.classes.getClassGen(str) != null) {
					return this.findClassInJars(str);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
			return super.findClass(str);
		} catch (final ClassNotFoundException f) {
			try {
				return this.findClassInJars(str);
			} catch (final Exception e) {
				throw f;
			}
		}
	}

	public static Class<?> forName(String str) {
		try {
			return Class.forName(str);
		} catch (final ClassNotFoundException e) {
			throw new NoClassDefFoundError(e.getMessage());
		}
	}

	public void setClasses(ClassStorage cs) {
		this.classes = cs;
	}

	public URLClassLoaderImpl(URL[] urlArr) {
		super(urlArr, (classLoaderClass == null ? (classLoaderClass = forName("com.allatori.URLClassLoaderImpl")) : classLoaderClass).getClassLoader());
	}

	private byte[] readZipEntry(JarFile jar, ZipEntry entry) throws IOException {
		final byte[] byteArray = new byte[(int) entry.getSize()];
		final DataInputStream dataInput = new DataInputStream(jar.getInputStream(entry));
		dataInput.readFully(byteArray, 0, byteArray.length);
		dataInput.close();
		return byteArray;
	}

	private Class<?> findClassInJars(String className) throws Exception {
		final String fullClassName = className.replace('.', '/').concat(".class");
		final Vector<?> vector = Class45.method660();
		for (int i = 0; i < vector.size(); i++) {
			final Class149 cls = (Class149) vector.get(i);
			final JarFile jar = new JarFile(cls.aString799);
			try {
				ZipEntry entry;
				if ((entry = jar.getEntry(fullClassName)) != null) {
					final byte[] byteArray = this.readZipEntry(jar, entry);
					return this.defineClass(className, byteArray, 0, byteArray.length);
				}
			} finally {
				jar.close();
			}
		}
		throw new Exception();
	}
}
