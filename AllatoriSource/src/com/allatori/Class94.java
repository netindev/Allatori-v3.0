package com.allatori;

public class Class94 implements Interface22 {

	private final ObfuscationStyle aClass105_1159 = new ObfuscationStyle();
	private int anInt1160 = 0;
	private final String[] aStringArray1161;

	@Override
	public String method262() {
		return this.anInt1160 < this.aStringArray1161.length ? this.aStringArray1161[this.anInt1160++]
				: this.aClass105_1159.method262();
	}

	public Class94(String[] var1) {
		this.aStringArray1161 = var1;
	}

	@Override
	public void method263() {
		this.anInt1160 = 0;
		this.aClass105_1159.method263();
	}
}
