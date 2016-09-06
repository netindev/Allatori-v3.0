package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class Class171 {

	private final Method method;
	private final ClassGen classGen;
	private final MethodGen methodGen;

	public static MethodGen getMethodGen(Class171 c) {
		return c.methodGen;
	}

	public static ClassGen getClassGen(Class171 c) {
		return c.classGen;
	}

	public Class171(ClassGen classGen, Method method) {
		this.classGen = classGen;
		this.method = method;
		this.methodGen = InitUtils.createMethodGen(method, classGen.getClassName(), classGen.getConstantPool(),
				classGen.getConstantPool().getConstantPool());
	}

	public static Method getMethod(Class171 c) {
		return c.method;
	}
}
