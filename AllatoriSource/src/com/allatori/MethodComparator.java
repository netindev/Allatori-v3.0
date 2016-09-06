package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.util.BCELComparator;

public class MethodComparator implements BCELComparator {

	public static MethodComparator instance() {
		return new MethodComparator();
	}

	@Override
	public int hashCode(Object obj) {
		Method method = (Method) obj;
		return method.getSignature().hashCode() ^ method.getName().hashCode();
	}

	@Override
	public boolean equals(Object comp, Object toComp) {
		final Method c = (Method) comp;
		final Method toC = (Method) toComp;
		return c.getName().equals(toC.getName()) && c.getSignature().equals(toC.getSignature());
	}
}
