package com.allatori;

public class Class69 implements IObfStyle {

	private final String str;

	public Class69() {
		this.str = "a";
	}

	@Override
	public void resetNext() {}

	@Override
	public String nextChar() {
		return this.str;
	}
}
