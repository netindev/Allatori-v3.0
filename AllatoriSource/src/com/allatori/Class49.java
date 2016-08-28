package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.util.BCELComparator;

public class Class49 implements BCELComparator {

	@Override
	public int hashCode(Object var1) {
		return ((ClassGen) var1).getClassName().hashCode();
	}

	@Override
	public boolean equals(Object var1, Object var2) {
		final ClassGen var3 = (ClassGen) var1;
		final ClassGen var4 = (ClassGen) var2;
		return var3.getClassName().equals(var4.getClassName());
	}
}
