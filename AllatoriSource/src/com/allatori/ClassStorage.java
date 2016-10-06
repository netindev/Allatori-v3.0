package com.allatori;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

public class ClassStorage {

	/* OK */

	private final Hashtable<String, ClassGen> table = new Hashtable<String, ClassGen>();
	private final ClassLoader classLoader;
	private final Vector<ClassGen> vector = new Vector<ClassGen>();

	public ClassGen getClassGen(String string) {
		return this.table.get(string);
	}

	public Iterator<ClassGen> valuesIterator() {
		return this.table.values().iterator();
	}

	public void put(ClassGen classGen) {
		this.table.put(classGen.getClassName(), classGen);
	}

	public void addClass(ClassGen classGen) {
		this.table.put(classGen.getClassName(), classGen);
		this.vector.add(classGen);
	}

	public Vector<ClassGen> getVector() {
		return this.vector;
	}

	public ClassStorage(URLClassLoaderImpl urlClassLoaderImpl) {
		this.classLoader = urlClassLoaderImpl;
		urlClassLoaderImpl.setClasses(this);
	}

	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
}
