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

	public static void parseClass(String string, ClassStorage classStorage) throws Exception {
		JarFile jarFile = new JarFile(string);
		for (Enumeration<?> enumeration = jarFile.entries(); enumeration
				.hasMoreElements(); enumeration.nextElement()) {
			JarEntry parse = (JarEntry) enumeration.nextElement();
			if (!parse.isDirectory() && parse.getName().endsWith(".class")) {
				try {
					classStorage.put(parseClass(jarFile.getInputStream(parse), parse.getName()));
				} catch (final Exception e) {
					Logger.printError("Cannot parse class " + parse.getName());
				}
			}
		}
		jarFile.close();
	}
}
