package com.allatori;

import java.util.Comparator;

import org.apache.bcel.generic.ClassGen;

public class InterfaceComparator implements Comparator<Object> {

	/* OK */

	@Override
	public int compare(Object toCompare0, Object toCompare1) {
		final boolean toCompareBool0 = ((ClassGen) toCompare0).isInterface();
		final boolean toCompareBool1 = ((ClassGen) toCompare1).isInterface();
		return toCompareBool0 == toCompareBool1 ? 0 : (toCompareBool0 ? -1 : 1);
	}
}
