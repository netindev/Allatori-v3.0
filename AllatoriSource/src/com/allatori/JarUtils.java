package com.allatori;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.generic.ClassGen;

@SuppressWarnings("resource")
public class JarUtils {

	private static Hashtable<String, String> fileTmp;

	public static void readAndPut(String[] stringArr, String file) throws Exception {
		final byte[] byteArr = new byte[65536];
		final JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file + ".tmp"));
		final Hashtable<String, String> hashtable = new Hashtable<String, String>();
		for (int i = 0; i < stringArr.length; i++) {
			final JarFile jarFile = new JarFile(stringArr[i]);
			final Enumeration<?> temp = jarFile.entries();
			for (Enumeration<?> enumeration = jarFile.entries(); enumeration.hasMoreElements(); enumeration = temp) {
				final JarEntry jarEntry = (JarEntry) temp.nextElement();
				if (!hashtable.containsKey(jarEntry.getName().toLowerCase())) {
					hashtable.put(jarEntry.getName().toLowerCase(), "");
					final InputStream inputStream = jarFile.getInputStream(jarEntry);
					final JarEntry jarEntryNext = new JarEntry(jarEntry.getName());
					jarOutputStream.putNextEntry(jarEntryNext);
					int read;
					for (InputStream input = inputStream; (read = input.read(byteArr)) > 0; input = inputStream) {
						jarOutputStream.write(byteArr, 0, read);
					}
					jarOutputStream.closeEntry();
				}
			}
			jarFile.close();
		}
		if (!Tuning.isWeakStringEncryption()) {
			setComment(jarOutputStream);
		}
		jarOutputStream.finish();
		jarOutputStream.close();
		canChange(file + ".tmp", file);
	}

	private static void method447(String var0, JarOutputStream var1) throws Exception {
		String var2 = "";

		for (String var10000 = var0; var10000.indexOf(47) > 0; var10000 = var0) {
			var2 = var2 + var0.substring(0, var0.indexOf(47) + 1);
			var0 = var0.substring(var0.indexOf(47) + 1);
			if (!fileTmp.containsKey(var2)) {
				fileTmp.put(var2, ".tmp");
				var1.putNextEntry(new JarEntry(var2));
				var1.closeEntry();
			}
		}
	}

	private static void setComment(JarOutputStream jarOutputStream) {
		jarOutputStream.setComment("Obfuscation by " + Info.name() + " " + Info.version() + "\n" + Info.website());
	}

	private static void method449(Vector<ClassGen> var0, JarOutputStream var1, String var2) throws Exception {
		int var3;
		for (int var10000 = var3 = var0.size() - 1; var10000 >= 0; var10000 = var3) {
			method451(var0.get(var3), var1, var2);
			--var3;
		}

	}

	public static void method450(String var0, String var1, ClassStorage var2) throws Exception {
		fileTmp = new Hashtable<String, String>();
		final byte[] var3 = new byte[65536];
		String var4 = null;
		final JarFile var5 = new JarFile(var0);
		final JarOutputStream var6 = new JarOutputStream(new FileOutputStream(var1 + ".tmp"));
		final Vector<ClassGen> var7 = new Vector<ClassGen>();

		Enumeration<?> var8;
		for (Enumeration<?> var10000 = var8 = var5.entries(); var10000.hasMoreElements(); var10000 = var8) {
			JarEntry var9;
			if (!(var9 = (JarEntry) var8.nextElement()).isDirectory()) {
				if (var9.getName().endsWith(".class")) {
					if (var4 == null) {
						var4 = method453(var5, var9);
					}

					String var10 = (var10 = var9.getName()).substring(var4.length(), var10.length() - 6).replace('/',
							'.');
					ClassGen var11;
					if ((var11 = var2.getClassGen(var10)) != null) {
						var7.add(var11);
						continue;
					}
				}

				final InputStream var15 = var5.getInputStream(var9);
				final JarEntry var14 = new JarEntry(var9.getName());
				method447(var9.getName(), var6);
				var6.putNextEntry(var14);

				int var12;
				for (InputStream var13 = var15; (var12 = var13.read(var3)) > 0; var13 = var15) {
					var6.write(var3, 0, var12);
				}

				var6.closeEntry();
			}
		}

		method449(var7, var6, var4);
		method449(var2.getVector(), var6, var4);
		var5.close();
		if (!Tuning.isWeakStringEncryption()) {
			setComment(var6);
		}

		var6.finish();
		var6.close();
		canChange(var1 + ".tmp", var1);
	}

	private static void method451(ClassGen classGen, JarOutputStream jarOutputStream, String string) throws Exception {
		String className = classGen.getClassName();
		className = string + className.replace('.', '/') + ".class";
		final JarEntry jarEntry = new JarEntry(className);
		method447(className, jarOutputStream);
		try {
			jarOutputStream.putNextEntry(jarEntry);
			jarOutputStream.write(classGen.getJavaClass().getBytes());
			jarOutputStream.closeEntry();
		} catch (final Exception var6) {
			/* empty */
		}
	}

	private static void canChange(String first, String second) {
		final File firstFile = new File(first);
		final File secondFile = new File(second);
		if (secondFile.exists() && !secondFile.delete()) {
			Logger.printWarning("Cannot delete \'" + secondFile.getPath() + "\'");
		}
		if (!firstFile.renameTo(secondFile)) {
			Logger.printWarning("Cannot rename \'" + firstFile.getPath() + "\' to \'" + secondFile.getPath() + "\'");
			Logger.printWarning("Resulting file is \'" + firstFile.getPath() + "\'");
		}
	}

	private static String method453(JarFile var0, JarEntry var1) {
		try {
			final InputStream var2 = var0.getInputStream(var1);
			final ClassGen var3 = new ClassGen((new ClassParser(var2, var1.getName())).parse());
			var2.close();
			return var1.getName().substring(0, var1.getName().length() - var3.getClassName().length() - 6);
		} catch (final Exception e) {
			return "";
		}
	}
}
