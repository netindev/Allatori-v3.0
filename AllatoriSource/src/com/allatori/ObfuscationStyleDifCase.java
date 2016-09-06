package com.allatori;

public class ObfuscationStyleDifCase implements IObfStyle {
	
	/* OK */

	private final String obfuscationStyle = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
	private long next;
	private final int obfuscationStyleLength;

	public ObfuscationStyleDifCase() {
		this.obfuscationStyleLength = this.obfuscationStyle.length();
		this.next = 0L;
	}

	@Override
	public void resetNext() {
		this.next = 0L;
	}

	@Override
	public String nextChar() {
		String toReturn = "";
		long next = this.next;
		do {
			toReturn = toReturn + this.obfuscationStyle.charAt((int) (next % this.obfuscationStyleLength));
		} while ((next /= this.obfuscationStyleLength) > 0L);
		++this.next;
		return toReturn;
	}
}
