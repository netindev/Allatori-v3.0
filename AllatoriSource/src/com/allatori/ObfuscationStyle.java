package com.allatori;

public class ObfuscationStyle implements Interface22 {

	private final String obfuscationStyle = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
	private long pLong;
	private final int obfuscationStyleLength;

	public ObfuscationStyle() {
		this.obfuscationStyleLength = this.obfuscationStyle.length();
		this.pLong = 0L;
	}

	@Override
	public void method263() {
		this.pLong = 0L;
	}

	@Override
	public String method262() {
		String emptyString = "";
		long lLong = this.pLong;
		do {
			emptyString = emptyString + this.obfuscationStyle.charAt((int) (lLong % this.obfuscationStyleLength));
		} while ((lLong /= this.obfuscationStyleLength) > 0L);
		++this.pLong;
		return emptyString;
	}
}
