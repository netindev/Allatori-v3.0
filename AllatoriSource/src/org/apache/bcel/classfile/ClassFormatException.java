/* ClassFormatException - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

public class ClassFormatException extends RuntimeException {
	private static final long serialVersionUID = -3569097343160139969L;

	public ClassFormatException() {
		/* empty */
	}

	public ClassFormatException(String s) {
		super(s);
	}

	public ClassFormatException(String s, Throwable initCause) {
		super(s, initCause);
	}
}
