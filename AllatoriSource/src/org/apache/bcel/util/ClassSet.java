/* ClassSet - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.util;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.JavaClass;

public class ClassSet implements Serializable
{
    private static final long serialVersionUID = -7476907380350035254L;
    private Map _map = new HashMap();
    
    public boolean add(JavaClass clazz) {
	boolean result = false;
	if (!_map.containsKey(clazz.getClassName())) {
	    result = true;
	    _map.put(clazz.getClassName(), clazz);
	}
	return result;
    }
    
    public void remove(JavaClass clazz) {
	_map.remove(clazz.getClassName());
    }
    
    public boolean empty() {
	return _map.isEmpty();
    }
    
    public JavaClass[] toArray() {
	Collection values = _map.values();
	JavaClass[] classes = new JavaClass[values.size()];
	values.toArray(classes);
	return classes;
    }
    
    public String[] getClassNames() {
	return ((String[])
		_map.keySet().toArray(new String[_map.keySet().size()]));
    }
}
