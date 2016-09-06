package com.allatori;

public class ObfuscationStyleCon implements IObfStyle {
	
	/* OK */

	private final ObfuscationStyleDifCase obfStyle = new ObfuscationStyleDifCase();
	private final String[] strArr;
	private int i = 0;

	@Override
	public String nextChar() {
		return this.i < this.strArr.length ? this.strArr[this.i++]
				: this.obfStyle.nextChar();
	}

	public ObfuscationStyleCon(String[] strArr) {
		this.strArr = strArr;
	}

	@Override
	public void resetNext() {
		this.i = 0;
		this.obfStyle.resetNext();
	}
}
