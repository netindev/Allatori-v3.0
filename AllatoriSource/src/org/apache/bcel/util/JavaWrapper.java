package org.apache.bcel.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JavaWrapper {
	private java.lang.ClassLoader loader;

	private static java.lang.ClassLoader getClassLoader() {
		String s = System.getProperty("bcel.classloader");
		if (s == null || "".equals(s))
			s = "org.apache.bcel.util.ClassLoader";
		java.lang.ClassLoader classloader;
		try {
			classloader = (java.lang.ClassLoader) Class.forName(s).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.toString(), e);
		}
		return classloader;
	}

	public JavaWrapper(java.lang.ClassLoader loader) {
		this.loader = loader;
	}

	public JavaWrapper() {
		this(getClassLoader());
	}

	public void runMain(String class_name, String[] argv) throws ClassNotFoundException {
		Class cl = loader.loadClass(class_name);
		Method method = null;
		try {
			method = cl.getMethod("main", new Class[] { argv.getClass() });
			int m = method.getModifiers();
			Class r = method.getReturnType();
			if (!Modifier.isPublic(m) || !Modifier.isStatic(m) || Modifier.isAbstract(m) || r != Void.TYPE)
				throw new NoSuchMethodException();
		} catch (NoSuchMethodException no) {
			System.out.println(new StringBuilder().append("In class ").append(class_name)
					.append(": public static void main(String[] argv) is not defined").toString());
			return;
		}
		try {
			method.invoke(null, new Object[] { argv });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] argv) throws Exception {
		if (argv.length == 0)
			System.out.println("Missing class name.");
		else {
			String class_name = argv[0];
			String[] new_argv = new String[argv.length - 1];
			System.arraycopy(argv, 1, new_argv, 0, new_argv.length);
			JavaWrapper wrapper = new JavaWrapper();
			wrapper.runMain(class_name, new_argv);
		}
	}
}
