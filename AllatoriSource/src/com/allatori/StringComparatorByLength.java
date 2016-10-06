package com.allatori;

import java.util.Comparator;

public class StringComparatorByLength implements Comparator<Object> {

	/* OK */

	@Override
	public int compare(Object toComp0, Object toComp1) {
		final String fString = (String) toComp0;
		final String sString = (String) toComp1;
		return fString.length() - sString.length();
	}
}
