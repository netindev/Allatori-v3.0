package com.allatori;

public class ObfuscationStyleA implements IObfStyle {
	
	/* OK */

	private final String string;

	public ObfuscationStyleA() {
		this.string = "a";
	}

	@Override
	public void resetNext() {}

	@Override
	public String nextChar() {
		return this.string;
	}
}
