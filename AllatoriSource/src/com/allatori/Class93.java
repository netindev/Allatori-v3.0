package com.allatori;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.util.BCELComparator;

public class Class93 implements BCELComparator {

	@Override
	public int hashCode(Object var1) {
		return ((Constant) var1).toString().hashCode();
	}

	@Override
	public boolean equals(Object var1, Object var2) {
		final Constant var3 = (Constant) var1;
		final Constant var4 = (Constant) var2;
		return var3.toString().equals(var4.toString());
	}
}
