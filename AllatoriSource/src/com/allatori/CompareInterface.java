package com.allatori;

import java.util.Comparator;

import org.apache.bcel.generic.ClassGen;

public class CompareInterface implements Comparator<Object> {

	@Override
	public int compare(Object comp, Object toComp) {
		final boolean isInterface0 = ((ClassGen) comp).isInterface();
		final boolean isInterface1 = ((ClassGen) toComp).isInterface();
		return isInterface0 == isInterface1 ? 0 : (isInterface0 ? -1 : 1);
	}
}
