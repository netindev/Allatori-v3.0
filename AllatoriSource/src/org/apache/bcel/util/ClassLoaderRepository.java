/* ClassLoaderRepository - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

public class ClassLoaderRepository implements Repository
{
    private static final long serialVersionUID = -1052781833503868187L;
    private java.lang.ClassLoader loader;
    private Map loadedClasses = new HashMap();
    
    public ClassLoaderRepository(java.lang.ClassLoader loader) {
	this.loader = loader;
    }
    
    public void storeClass(JavaClass clazz) {
	loadedClasses.put(clazz.getClassName(), clazz);
	clazz.setRepository(this);
    }
    
    public void removeClass(JavaClass clazz) {
	loadedClasses.remove(clazz.getClassName());
    }
    
    public JavaClass findClass(String className) {
	if (loadedClasses.containsKey(className))
	    return (JavaClass) loadedClasses.get(className);
	return null;
    }
    
    public JavaClass loadClass(String className)
	throws ClassNotFoundException {
	String classFile = className.replace('.', '/');
	JavaClass RC = findClass(className);
	if (RC != null)
	    return RC;
	JavaClass javaclass;
	try {
	    InputStream is
		= loader.getResourceAsStream(new StringBuilder().append
						 (classFile).append
						 (".class").toString());
	    if (is == null)
		throw new ClassNotFoundException(new StringBuilder().append
						     (className).append
						     (" not found.")
						     .toString());
	    ClassParser parser = new ClassParser(is, className);
	    RC = parser.parse();
	    storeClass(RC);
	    javaclass = RC;
	} catch (IOException e) {
	    throw new ClassNotFoundException(new StringBuilder().append
						 (className).append
						 (" not found: ").append
						 (e.toString()).toString(),
					     e);
	}
	return javaclass;
    }
    
    public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
	return loadClass(clazz.getName());
    }
    
    public void clear() {
	loadedClasses.clear();
    }
    
    public ClassPath getClassPath() {
	return null;
    }
}
