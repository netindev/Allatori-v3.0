package com.allatori;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

public class DumpUtils {
	
	/* OK */

	private static String file;

	private static void writeClassGenToFile(ClassGen classGen, File file) throws Exception {
		file.getParentFile().mkdirs();
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
		bufferedOutputStream.write(classGen.getJavaClass().getBytes());
		bufferedOutputStream.close();
	}

	private static void dumpClassStorage(File fileD, File fileS, ClassStorage classStorage, String string) throws Exception {
		final File[] files = fileD.listFiles();
		ClassGen classGen;
		String classNameR;
		for (int i = 0; i < files.length; i++) {
			File fileName = files[i];
			String className = string + "." + fileName.getName();
			if (fileName.isDirectory()) {
				if (className.startsWith(".")) {
					className = className.substring(1);
				}
				dumpClassStorage(fileName, new File(fileS.getPath() + "/" + fileName.getName()), classStorage, className);
			} else if (fileName.getName().endsWith(".class")) {
				className = (className = fileName.getName()).substring(0, className.length() - 6);
				if (string.length() > 0) {
					className = string + "." + className;
				}
				if ((classGen = classStorage.getClassGen(className)) != null) {
					classNameR = classGen.getClassName();
					classNameR = classNameR.replace('.', '/') + ".class";
					writeClassGenToFile(classGen, new File(file + "/" + classNameR));
				}
			} else {
				(new File(fileS.getPath())).mkdirs();
				FileUtils.writeMap(fileName, new File(fileS.getPath() + "/" + fileName.getName()));
			}
		}
		final Vector<?> vector = classStorage.getVector();
		for (int i = 0; i < vector.size(); i++) {
			classNameR = (classGen = (ClassGen) vector.get(i)).getClassName();
			classNameR = classNameR.replace('.', '/') + ".class";
			writeClassGenToFile(classGen, new File(file + "/" + classNameR));
		}
	}

	public static void dumpClassStorage(String fileD, String filePath, ClassStorage classStorage) throws Exception {
		file = filePath;
		dumpClassStorage(new File(fileD), new File(filePath), classStorage, "");
	}
}
