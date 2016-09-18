package com.allatori;

public class ObfuscationStyle implements IObfuscationStyle {

	private final ObfuscationStyleMixed aClass105_1159 = new ObfuscationStyleMixed();
	private int anInt1160 = 0;
	private final String[] aStringArray1161;

	@Override
	public String next() {
		return this.anInt1160 < this.aStringArray1161.length ? this.aStringArray1161[this.anInt1160++]
				: this.aClass105_1159.next();
	}

	public ObfuscationStyle(String[] var1) {
		this.aStringArray1161 = var1;
	}

	@Override
	public void reset() {
		this.anInt1160 = 0;
		this.aClass105_1159.reset();
	}
}
