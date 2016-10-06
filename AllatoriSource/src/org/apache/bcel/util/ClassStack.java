package org.apache.bcel.util;

import java.io.Serializable;
import java.util.Stack;

import org.apache.bcel.classfile.JavaClass;

public class ClassStack implements Serializable {
	private static final long serialVersionUID = 6126079269396985982L;
	private final Stack<JavaClass> stack = new Stack<JavaClass>();

	public void push(JavaClass clazz) {
		stack.push(clazz);
	}

	public JavaClass pop() {
		return stack.pop();
	}

	public JavaClass top() {
		return stack.peek();
	}

	public boolean empty() {
		return stack.empty();
	}
}
