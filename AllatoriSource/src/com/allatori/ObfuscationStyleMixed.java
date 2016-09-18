package com.allatori;

public class ObfuscationStyleMixed implements IObfuscationStyle {

	// arryn

	private final String aString1162 = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
	private long aLong1163;
	private final int anInt1164;

	public ObfuscationStyleMixed() {
		this.anInt1164 = this.aString1162.length();
		this.aLong1163 = 0L;
	}

	@Override
	public void reset() {
		this.aLong1163 = 0L;
	}

	@Override
	public String next() {
		String var1 = "";
		long var2 = this.aLong1163;

		do {
			var1 = var1 + this.aString1162.charAt((int) (var2 % this.anInt1164));
		} while ((var2 /= this.anInt1164) > 0L);

		++this.aLong1163;
		return var1;
	}
}
