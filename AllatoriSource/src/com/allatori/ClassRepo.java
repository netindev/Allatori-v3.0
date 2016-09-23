package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class ClassRepo {
	
	/* OK */

	private final Method method;
	private final ClassGen classGen;
	private final MethodGen methodGen;

	public static MethodGen getMethodGen(ClassRepo classRepo) {
		return classRepo.methodGen;
	}

	public static ClassGen getClassGen(ClassRepo classRepo) {
		return classRepo.classGen;
	}

	public ClassRepo(ClassGen classGen, Method method) {
		this.classGen = classGen;
		this.method = method;
		this.methodGen = InitUtils.createMethodGen(method, classGen.getClassName(), classGen.getConstantPool(),
				classGen.getConstantPool().getConstantPool());
	}

	public static Method getMethod(ClassRepo classRepo) {
		return classRepo.method;
	}
}
