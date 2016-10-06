package com.allatori;

import java.util.Comparator;

import org.apache.bcel.generic.ClassGen;

public class ClassGenComparator implements Comparator<Object> {

	/* OK */

	public ClassGenComparator() {
		/* empty */
	}

	@Override
	public int compare(Object toComp0, Object toComp1) {
		final String fClassName = ((ClassGen) toComp0).getClassName();
		final String sClassName = ((ClassGen) toComp1).getClassName();
		return fClassName.compareTo(sClassName);
	}
}
