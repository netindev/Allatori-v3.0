/* ClassStack - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.util;
import java.io.Serializable;
import java.util.Stack;

import org.apache.bcel.classfile.JavaClass;

public class ClassStack implements Serializable
{
    private static final long serialVersionUID = 6126079269396985982L;
    private Stack stack = new Stack();
    
    public void push(JavaClass clazz) {
	stack.push(clazz);
    }
    
    public JavaClass pop() {
	return (JavaClass) stack.pop();
    }
    
    public JavaClass top() {
	return (JavaClass) stack.peek();
    }
    
    public boolean empty() {
	return stack.empty();
    }
}
