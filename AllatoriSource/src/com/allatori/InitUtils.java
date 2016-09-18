package com.allatori;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;

public class InitUtils {
	
	/* OK */

	public static MethodGen createMethodGen(Method method, String owner, ConstantPoolGen constantPoolGen, ConstantPool constantPool) {
		method.setConstantPool(constantPool);
		return new MethodGen(method, owner, constantPoolGen);
	}
}
