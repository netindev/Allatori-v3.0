package com.allatori;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

public class ClassLoadingUtils {
	
	/* OK */
	
	private static ClassGen parseClassGen(InputStream input, String file) throws Exception {
		final ClassGen classGen = new ClassGen((new ClassParser(input, file)).parse());
		input.close();
		return classGen;
	}

	public static void loadClassesFromDir(String file, ClassStorage classStorage) throws Exception {
		final File[] listFiles = new File(file).listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			final File actualFile = listFiles[i];
			if (actualFile.isDirectory()) {
				loadClassesFromDir(actualFile.getPath(), classStorage);
			}
			if (actualFile.getName().endsWith(".class")) {
				try {
					classStorage.put(parseClassGen(new BufferedInputStream(new FileInputStream(actualFile)), actualFile.getName()));
				} catch (final Exception e) {
					Logger.printError("Cannot parse class " + actualFile.getName());
				}
			}
		}
	}
}
