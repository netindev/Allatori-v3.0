package com.allatori;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;

public class InitUtils {
	
	/* OK */

	public static MethodGen createMethodGen(Method m, String owner, ConstantPoolGen cpg, ConstantPool cp) {
		m.setConstantPool(cp);
		return new MethodGen(m, owner, cpg);
	}
}
