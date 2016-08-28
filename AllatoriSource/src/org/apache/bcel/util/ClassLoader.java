package org.apache.bcel.util;

import java.io.ByteArrayInputStream;
import java.util.Hashtable;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;

public class ClassLoader extends java.lang.ClassLoader {
	public static final String[] DEFAULT_IGNORED_PACKAGES = { "java.", "javax.", "sun." };
	private final Hashtable classes;
	private final String[] ignored_packages;
	private Repository repository;

	public ClassLoader() {
		this(DEFAULT_IGNORED_PACKAGES);
	}

	public ClassLoader(java.lang.ClassLoader deferTo) {
		super(deferTo);
		classes = new Hashtable();
		repository = SyntheticRepository.getInstance();
		ignored_packages = DEFAULT_IGNORED_PACKAGES;
		repository = new ClassLoaderRepository(deferTo);
	}

	public ClassLoader(String[] ignored_packages) {
		classes = new Hashtable();
		repository = SyntheticRepository.getInstance();
		this.ignored_packages = ignored_packages;
	}

	public ClassLoader(java.lang.ClassLoader deferTo, String[] ignored_packages) {
		this(ignored_packages);
		repository = new ClassLoaderRepository(deferTo);
	}

	@Override
	protected Class loadClass(String class_name, boolean resolve) throws ClassNotFoundException {
		Class cl = null;
		if ((cl = (Class) classes.get(class_name)) == null) {
			for (int i = 0; i < ignored_packages.length; i++) {
				if (class_name.startsWith(ignored_packages[i])) {
					cl = getParent().loadClass(class_name);
					break;
				}
			}
			if (cl == null) {
				JavaClass clazz = null;
				if (class_name.indexOf("$$BCEL$$") >= 0)
					clazz = createClass(class_name);
				else if ((clazz = repository.loadClass(class_name)) != null)
					clazz = modifyClass(clazz);
				else
					throw new ClassNotFoundException(class_name);
				if (clazz != null) {
					final byte[] bytes = clazz.getBytes();
					cl = defineClass(class_name, bytes, 0, bytes.length);
				} else
					cl = Class.forName(class_name);
			}
			if (resolve)
				resolveClass(cl);
		}
		classes.put(class_name, cl);
		return cl;
	}

	protected JavaClass modifyClass(JavaClass clazz) {
		return clazz;
	}

	protected JavaClass createClass(String class_name) {
		final int index = class_name.indexOf("$$BCEL$$");
		final String real_name = class_name.substring(index + 8);
		JavaClass clazz = null;
		try {
			final byte[] bytes = Utility.decode(real_name, true);
			final ClassParser parser = new ClassParser(new ByteArrayInputStream(bytes), "foo");
			clazz = parser.parse();
		} catch (final Throwable e) {
			e.printStackTrace();
			return null;
		}
		final ConstantPool cp = clazz.getConstantPool();
		final ConstantClass cl = ((ConstantClass) cp.getConstant(clazz.getClassNameIndex(), (byte) 7));
		final ConstantUtf8 name = (ConstantUtf8) cp.getConstant(cl.getNameIndex(), (byte) 1);
		name.setBytes(class_name.replace('.', '/'));
		return clazz;
	}
}
