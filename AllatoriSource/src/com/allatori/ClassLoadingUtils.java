package com.allatori;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

public class ClassLoadingUtils {
	
	/* OK */

	private static ClassGen parseClassGen(InputStream inputStream, String str) throws Exception {
		ClassGen classGen = new ClassGen(new ClassParser(inputStream, str).parse());
		inputStream.close();
		return classGen;
	}

	public static void loadClassesFromDir(String path, ClassStorage classStorage) throws Exception {
		File[] fileArray = (new File(path)).listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			final File file = fileArray[i];
			if (file.isDirectory()) {
				loadClassesFromDir(file.getPath(), classStorage);
			}
			if (file.getName().endsWith(".class")) {
				try {
					classStorage.put(parseClassGen(new BufferedInputStream(new FileInputStream(file)), file.getName()));
				} catch (final Exception var7) {
					Logger.printError("Cannot parse class " + file.getName());
				}
			}
			++i;
		}
	}
}
