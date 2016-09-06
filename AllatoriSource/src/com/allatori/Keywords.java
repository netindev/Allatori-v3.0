package com.allatori;

import java.util.Arrays;

public class Keywords extends Class94 {
	
	/* OK */

	private static final String[] KEYWORDS = new String[] { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
			"char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false",
			"final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try",
			"void", "volatile", "while" };

	public Keywords() {
		super(KEYWORDS);
	}

	static {
		Arrays.sort(KEYWORDS, new StringComparatorByLength());
	}
}
