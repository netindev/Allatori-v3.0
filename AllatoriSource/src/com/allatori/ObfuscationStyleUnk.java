package com.allatori;

public class ObfuscationStyleUnk implements IObfuscationStyle {
	
	/* OK */

	private final String type;

	public ObfuscationStyleUnk() {
		this.type = "a";
	}

	@Override
	public void reset() {
		/* empty */
	}

	@Override
	public String next() {
		return this.type;
	}
}
