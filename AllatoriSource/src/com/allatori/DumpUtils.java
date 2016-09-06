package com.allatori;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

public class DumpUtils {

	/* OK */
	
	private static String string;

	private static void writeClassGenToFile(ClassGen classGen, File file) throws Exception {
		file.getParentFile().mkdirs();
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
		bufferedOutputStream.write(classGen.getJavaClass().getBytes());
		bufferedOutputStream.close();
	}

	private static void dumpClassStorage(File toListFiles, File file, ClassStorage classStorage, String str) throws Exception {
		final File[] listFiles = toListFiles.listFiles();
		ClassGen classGen = null;
		String replace = null;
		for (int i = 0; i < listFiles.length; i++) {
			File actualFile = listFiles[i];
			String className;
			if (actualFile.isDirectory()) {
				if ((className = str + "." + actualFile.getName()).startsWith(".")) {
					className = className.substring(1);
				}
				dumpClassStorage(actualFile, new File(file.getPath() + "/" + actualFile.getName()), classStorage, className);
			} else if (actualFile.getName().endsWith(".class")) {
				className = (className = actualFile.getName()).substring(0, className.length() - 6);
				if (str.length() > 0) {
					className = str + "." + className;
				}
				if ((classGen = classStorage.getClassGen(className)) != null) {
					replace = classGen.getClassName();
					replace = replace.replace('.', '/') + ".class";
					writeClassGenToFile(classGen, new File(string + "/" + replace));
				}
			} else {
				new File(file.getPath()).mkdirs();
				FileUtils.map(actualFile, new File(file.getPath() + "/" + actualFile.getName()));
			}
		}
		final Vector<ClassGen> vector = classStorage.vector();
		for (int i = 0; i < vector.size(); i++) {
			replace = (classGen = (ClassGen) vector.get(i)).getClassName();
			replace = replace.replace('.', '/') + ".class";
			writeClassGenToFile(classGen, new File(string + "/" + replace));
		}
	}

	public static void dumpClass(String path, String loc, ClassStorage classStorage) throws Exception {
		string = loc;
		dumpClassStorage(new File(path), new File(loc), classStorage, "");
	}
}
