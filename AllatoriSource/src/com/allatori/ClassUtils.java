package com.allatori;

public class ClassUtils {
	
	/* OK */

	private static ClassConstraint classConstraint;
	private static String className;
	private static boolean passThis;
	private static String methodName;

	public static void setMethodName(String string) {
		methodName = string;
	}

	public static void setClassName(String string) {
		className = string;
	}

	public static void setPassThis(boolean setBool) {
		passThis = setBool;
	}

	public static boolean getPassThis() {
		return passThis;
	}

	public static ClassConstraint getClassConstraint() {
		return classConstraint;
	}

	public static String getClassName() {
		return className;
	}

	public static void setClassConstraint(ClassConstraint setClassConstraint) {
		classConstraint = setClassConstraint;
	}

	public static String getMethodName() {
		return methodName;
	}
}
