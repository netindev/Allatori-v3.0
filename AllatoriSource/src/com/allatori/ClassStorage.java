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

	public ClassGen getClassGen(String str) {
		return (ClassGen) this.table.get(str);
	}

	public Iterator<ClassGen> iterator() {
		return this.table.values().iterator();
	}

	public void put(ClassGen cg) {
		this.table.put(cg.getClassName(), cg);
	}

	public void addClass(ClassGen cg) {
		this.table.put(cg.getClassName(), cg);
		this.vector.add(cg);
	}

	public Vector<ClassGen> vector() {
		return this.vector;
	}

	public ClassStorage(URLClassLoaderImpl cli) {
		this.classLoader = cli;
		cli.setClasses(this);
	}

	public ClassLoader cLoader() {
		return this.classLoader;
	}
}
