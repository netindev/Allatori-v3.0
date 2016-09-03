package com.allatori;

public class TemplateException extends Exception {
	
	/* OK */

	private static final long serialVersionUID = 1L;

	public TemplateException(String str) {
		super(str);
	}

	public TemplateException(Throwable thr) {
		super(thr);
	}
}
