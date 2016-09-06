package com.allatori;

import java.util.Comparator;

public class StringComparatorByLength implements Comparator {

	@Override
	public int compare(Object var1, Object var2) {
		final String var3 = (String) var1;
		final String var4 = (String) var2;
		return var3.length() - var4.length();
	}
}
