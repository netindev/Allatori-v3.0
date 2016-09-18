package com.allatori;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class URLClassLoaderImpl extends URLClassLoader {

	private ClassStorage classes;
	public static Class<?> classLoaderClass;

	@Override
	public Class<?> findClass(String var1) throws ClassNotFoundException {
		try {
			try {
				if (this.classes.getClassGen(var1) != null) {
					return this.findClassInJars(var1);
				}
			} catch (final Exception var5) {
			}

			return super.findClass(var1);
		} catch (final ClassNotFoundException var6) {
			try {
				return this.findClassInJars(var1);
			} catch (final Exception var4) {
				throw var6;
			}
		}
	}

	public static Class<?> forName(String var0) {
		try {
			return Class.forName(var0);
		} catch (final ClassNotFoundException var2) {
			throw new NoClassDefFoundError(var2.getMessage());
		}
	}

	public void setClasses(ClassStorage var1) {
		this.classes = var1;
	}

	public URLClassLoaderImpl(URL[] var1) {
		super(var1, (classLoaderClass == null ? (classLoaderClass = forName("com.allatori.URLClassLoaderImpl"))
				: classLoaderClass).getClassLoader());
	}

	private byte[] readZipEntry(JarFile var1, ZipEntry var2) throws IOException {
		final byte[] var3 = new byte[(int) var2.getSize()];
		final DataInputStream var4 = new DataInputStream(var1.getInputStream(var2));
		var4.readFully(var3, 0, var3.length);
		var4.close();
		return var3;
	}

	private Class<?> findClassInJars(String className) throws Exception {
		final String fullClassName = className.replace('.', '/').concat(".class");
		final Vector<?> var3 = Configurable.method660();
		int var4;
		for (int var10000 = var4 = 0; var10000 < var3.size(); var10000 = var4) {
			final ConfigRepo var5 = (ConfigRepo) var3.get(var4);
			final JarFile var6 = new JarFile(var5.aString799);
			try {
				ZipEntry entry;
				if ((entry = var6.getEntry(fullClassName)) != null) {
					final byte[] var8 = this.readZipEntry(var6, entry);
					return this.defineClass(className, var8, 0, var8.length);
				}
			} finally {
				var6.close();
			}
			++var4;
		}
		throw new Exception();
	}
}
