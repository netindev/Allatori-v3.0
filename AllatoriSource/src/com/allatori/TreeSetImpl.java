package com.allatori;

import java.util.TreeSet;

public class TreeSetImpl extends TreeSet<Object> {
	
	/* OK */

	private static final long serialVersionUID = 1L;

	@Override
	public boolean contains(Object obj) {
		return true;
	}

	@Override
	public int size() {
		return Integer.MAX_VALUE;
	}
}
