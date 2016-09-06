package com.allatori;

public class ObfuscationStyleUnk implements IObfStyle {
	
	/* OK */

	private final String string;

	public ObfuscationStyleUnk() {
		this.string = "a";
	}

	@Override
	public void resetNext() {}

	@Override
	public String nextChar() {
		return this.string;
	}
}
