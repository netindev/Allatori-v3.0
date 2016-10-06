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
	public Class<?> findClass(String clazz) throws ClassNotFoundException {
		try {
			try {
				if (this.classes.getClassGen(clazz) != null) {
					return this.findClassInJars(clazz);
				}
			} catch (final Exception e) {
				/* empty */
			}
			return super.findClass(clazz);
		} catch (final ClassNotFoundException e) {
			try {
				return this.findClassInJars(clazz);
			} catch (final Exception f) {
				throw e;
			}
		}
	}

	public static Class<?> forName(String clazz) {
		try {
			return Class.forName(clazz);
		} catch (final ClassNotFoundException e) {
			throw new NoClassDefFoundError(e.getMessage());
		}
	}

	public void setClasses(ClassStorage classStorage) {
		this.classes = classStorage;
	}

	public URLClassLoaderImpl(URL[] urlArr) {
		super(urlArr, (classLoaderClass == null ? (classLoaderClass = forName("com.allatori.URLClassLoaderImpl"))
				: classLoaderClass).getClassLoader());
	}

	private byte[] readZipEntry(JarFile jarFile, ZipEntry zipEntry) throws IOException {
		final byte[] size = new byte[(int) zipEntry.getSize()];
		final DataInputStream dataInputStream = new DataInputStream(jarFile.getInputStream(zipEntry));
		dataInputStream.readFully(size, 0, size.length);
		dataInputStream.close();
		return size;
	}

	private Class<?> findClassInJars(String className) throws Exception {
		final String fullClassName = className.replace('.', '/').concat(".class");
		final Vector<?> vector = Configurable.getConfigRepo();
		for (int i = 0; i < vector.size(); i++) {
			final ConfigRepo configRepo = (ConfigRepo) vector.get(i);
			final JarFile jarFile = new JarFile(configRepo.key);
			try {
				final ZipEntry entry = jarFile.getEntry(fullClassName);
				if (entry != null) {
					final byte[] read = this.readZipEntry(jarFile, entry);
					return this.defineClass(className, read, 0, read.length);
				}
			} finally {
				jarFile.close();
			}
		}
		throw new Exception();
	}
}
