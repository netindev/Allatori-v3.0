package com.allatori;

import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.util.BCELComparator;

public class Class74 implements BCELComparator {

	@Override
	public boolean equals(Object var1, Object var2) {
		final MethodGen var3 = (MethodGen) var1;
		final MethodGen var4 = (MethodGen) var2;
		return var3.getName().equals(var4.getName()) && var3.getSignature().equals(var4.getSignature());
	}

	@Override
	public int hashCode(Object var1) {
		MethodGen var2;
		return (var2 = (MethodGen) var1).getSignature().hashCode() ^ var2.getName().hashCode();
	}
}
