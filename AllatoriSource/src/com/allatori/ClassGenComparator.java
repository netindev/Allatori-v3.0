package com.allatori;

import java.util.Comparator;

import org.apache.bcel.generic.ClassGen;

public class ClassGenComparator implements Comparator {

	public ClassGenComparator() {
	}

	@Override
	public int compare(Object var1, Object var2) {
		final String var3 = ((ClassGen) var1).getClassName();
		final String var4 = ((ClassGen) var2).getClassName();
		return var3.compareTo(var4);
	}
}
