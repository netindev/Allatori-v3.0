package com.allatori;

import org.apache.bcel.generic.ClassGen;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class ClassStorage {

    private Hashtable table = new Hashtable();
    private ClassLoader classLoader;
    private Vector vector = new Vector();


    public ClassGen getClassGen(String var1) {
        return (ClassGen) this.table.get(var1);
    }

    public Iterator method671() {
        return this.table.values().iterator();
    }

    public void method672(ClassGen var1) {
        this.table.put(var1.getClassName(), var1);
    }

    public void addClass(ClassGen var1) {
        this.table.put(var1.getClassName(), var1);
        this.vector.add(var1);
    }

    public Vector method674() {
        return this.vector;
    }

    public ClassStorage(URLClassLoaderImpl var1) {
        this.classLoader = var1;
        var1.setClasses(this);
    }

    public ClassLoader method675() {
        return this.classLoader;
    }
}
