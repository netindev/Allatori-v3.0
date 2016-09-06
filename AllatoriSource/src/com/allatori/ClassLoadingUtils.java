package com.allatori;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

public class ClassLoadingUtils {

	private static ClassGen parseClassGen(InputStream var0, String var1) throws Exception {
		final ClassGen var2 = new ClassGen((new ClassParser(var0, var1)).parse());
		var0.close();
		return var2;
	}

	public static void loadClassesFromDir(String var0, ClassStorage var1) throws Exception {
		final File[] var3 = (new File(var0)).listFiles();
		int var4 = 0;

		for (int var10000 = var4; var10000 < var3.length; var10000 = var4) {
			final File var5 = var3[var4];
			if (var5.isDirectory()) {
				loadClassesFromDir(var5.getPath(), var1);
			}

			if (var5.getName().endsWith(".class")) {
				try {
					var1.method672(parseClassGen(new BufferedInputStream(new FileInputStream(var5)), var5.getName()));
				} catch (final Exception var7) {
					Logger.printError("Cannot parse class " + var5.getName());
				}
			}

			++var4;
		}

	}
}
