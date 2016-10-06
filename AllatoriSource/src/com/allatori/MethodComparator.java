package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.util.BCELComparator;

public class MethodComparator implements BCELComparator {

	/* OK */

	private static MethodComparator methodComparator = new MethodComparator();

	public static MethodComparator instance() {
		return methodComparator;
	}

	@Override
	public int hashCode(Object obj) {
		final Method method = (Method) obj;
		return method.getSignature().hashCode() ^ method.getName().hashCode();
	}

	@Override
	public boolean equals(Object toCompare0, Object toCompare1) {
		final Method fMethod = (Method) toCompare0;
		final Method sMethod = (Method) toCompare1;
		return fMethod.getName().equals(sMethod.getName()) && fMethod.getSignature().equals(sMethod.getSignature());
	}
}
