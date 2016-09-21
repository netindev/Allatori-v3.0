package com.allatori;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

public class ParseClass {
	
	/* OK */

	private static ClassGen parseClass(InputStream inputStream, String string) throws Exception {
		final ClassGen classGen = new ClassGen(new ClassParser(inputStream, string).parse());
		inputStream.close();
		return classGen;
	}

	public static void parseClass(String file, ClassStorage classStorage) throws Exception {
		JarFile jarFile = new JarFile(file);
		Enumeration<?> actual = jarFile.entries();
		for (Enumeration<?> enumerationIt = actual; enumerationIt.hasMoreElements(); enumerationIt = actual) {
			JarEntry jarEntry = (JarEntry) actual.nextElement();
			if (!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
				try {
					classStorage.put(parseClass(jarFile.getInputStream(jarEntry), jarEntry.getName()));
				} catch (final Exception e) {
					Logger.printError("Cannot parse class " + jarEntry.getName());
				}
			}
		}
		jarFile.close();
	}
}
