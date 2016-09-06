package com.allatori;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

public class DumpUtils {

	private static String aString580;

	private static void writeClassGenToFile(ClassGen var0, File var1) throws Exception {
		var1.getParentFile().mkdirs();
		final BufferedOutputStream var3 = new BufferedOutputStream(new FileOutputStream(var1));
		var3.write(var0.getJavaClass().getBytes());
		var3.close();
	}

	private static void dumpClassStorage(File var0, File var1, ClassStorage cs, String var3) throws Exception {
		final File[] var4 = var0.listFiles();

		int var5;
		ClassGen var8;
		String var9;
		int var10000;
		for (var10000 = var5 = 0; var10000 < var4.length; var10000 = var5) {
			File var6;
			String className;
			if ((var6 = var4[var5]).isDirectory()) {
				if ((className = var3 + "." + var6.getName()).startsWith(".")) {
					className = className.substring(1);
				}

				dumpClassStorage(var6, new File(var1.getPath() + "/" + var6.getName()), cs, className);
			} else if (var6.getName().endsWith(".class")) {
				className = (className = var6.getName()).substring(0, className.length() - 6);
				if (var3.length() > 0) {
					className = var3 + "." + className;
				}

				if ((var8 = cs.getClassGen(className)) != null) {
					var9 = var8.getClassName();
					var9 = var9.replace('.', '/') + ".class";
					writeClassGenToFile(var8, new File(aString580 + "/" + var9));
				}
			} else {
				(new File(var1.getPath())).mkdirs();
				FileUtils.method689(var6, new File(var1.getPath() + "/" + var6.getName()));
			}

			++var5;
		}

		final Vector<ClassGen> var10 = cs.vector();

		int var11;
		for (var10000 = var11 = 0; var10000 < var10.size(); var10000 = var11) {
			var9 = (var8 = (ClassGen) var10.get(var11)).getClassName();
			var9 = var9.replace('.', '/') + ".class";
			writeClassGenToFile(var8, new File(aString580 + "/" + var9));
			++var11;
		}

	}

	public static void method574(String var0, String var1, ClassStorage var2) throws Exception {
		aString580 = var1;
		dumpClassStorage(new File(var0), new File(var1), var2, "");
	}
}
