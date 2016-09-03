package com.allatori;

public class Class94 implements Interface22 {

	private final ObfuscationStyle obfStyle = new ObfuscationStyle();
	private final String[] strArr;
	private int i = 0;

	@Override
	public String method262() {
		return this.i < this.strArr.length ? this.strArr[this.i++]
				: this.obfStyle.method262();
	}

	public Class94(String[] strArr) {
		this.strArr = strArr;
	}

	@Override
	public void method263() {
		this.i = 0;
		this.obfStyle.method263();
	}
}
