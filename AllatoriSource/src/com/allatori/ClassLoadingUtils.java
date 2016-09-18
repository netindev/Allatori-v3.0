package com.allatori;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

public class ClassLoadingUtils {

	private static ClassGen parseClassGen(InputStream input, String file) throws Exception {
		final ClassGen classGen = new ClassGen((new ClassParser(input, file)).parse());
		input.close();
		return classGen;
	}

	public static void loadClassesFromDir(String file, ClassStorage classStorage) throws Exception {
		final File[] var3 = (new File(file)).listFiles();
		int var4 = 0;

		for (int var10000 = var4; var10000 < var3.length; var10000 = var4) {
			final File var5 = var3[var4];
			if (var5.isDirectory()) {
				loadClassesFromDir(var5.getPath(), classStorage);
			}

			if (var5.getName().endsWith(".class")) {
				try {
					classStorage.put(parseClassGen(new BufferedInputStream(new FileInputStream(var5)), var5.getName()));
				} catch (final Exception var7) {
					Logger.printError("Cannot parse class " + var5.getName());
				}
			}

			++var4;
		}

	}
}
