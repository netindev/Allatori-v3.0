package com.allatori;

import java.util.Comparator;

import org.apache.bcel.generic.ClassGen;

public class Class35 implements Comparator<Object> {

	@Override
	public int compare(Object var1, Object var2) {
		final boolean var3 = ((ClassGen) var1).isInterface();
		final boolean var4 = ((ClassGen) var2).isInterface();
		return var3 == var4 ? 0 : (var3 ? -1 : 1);
	}
}
