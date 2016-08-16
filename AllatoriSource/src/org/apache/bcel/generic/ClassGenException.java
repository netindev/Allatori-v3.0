package org.apache.bcel.generic;

public class ClassGenException extends RuntimeException {
	private static final long serialVersionUID = 7247369755051242791L;

	public ClassGenException() {
	}

	public ClassGenException(String s) {
		super(s);
	}

	public ClassGenException(String s, Throwable initCause) {
		super(s, initCause);
	}
}
