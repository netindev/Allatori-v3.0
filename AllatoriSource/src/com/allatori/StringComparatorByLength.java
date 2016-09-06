package com.allatori;

import java.util.Comparator;

public class StringComparatorByLength implements Comparator<Object> {
	
	/* OK */

	@Override
	public int compare(Object obj0, Object obj1) {
		final String str0 = (String) obj0;
		final String str1 = (String) obj1;
		return str0.length() - str1.length();
	}
}
