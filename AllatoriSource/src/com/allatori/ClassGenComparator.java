package com.allatori;

import java.util.Comparator;

import org.apache.bcel.generic.ClassGen;

public class ClassGenComparator implements Comparator<Object> {
	
	/* OK */

	public ClassGenComparator() {}

	@Override
	public int compare(Object obj, Object toComp) {
		final String classNameObj = ((ClassGen) obj).getClassName();
		final String classNameToComp = ((ClassGen) toComp).getClassName();
		return classNameObj.compareTo(classNameToComp);
	}
}
