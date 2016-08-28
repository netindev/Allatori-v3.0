package com.allatori;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.BCELComparator;

public class Class86 implements BCELComparator {

	@Override
	public int hashCode(Object var1) {
		return ((JavaClass) var1).getClassName().hashCode();
	}

	@Override
	public boolean equals(Object var1, Object var2) {
		final JavaClass var3 = (JavaClass) var1;
		final JavaClass var4 = (JavaClass) var2;
		return var3.getClassName().equals(var4.getClassName());
	}
}
