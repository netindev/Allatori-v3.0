package com.allatori;

public class ObfuscationStyleMixed implements IObfuscationStyle {
	
	/* OK */

	private final String type = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
	private long next;
	private final int length;

	public ObfuscationStyleMixed() {
		this.length = this.type.length();
		this.next = 0L;
	}

	@Override
	public void reset() {
		this.next = 0L;
	}

	@Override
	public String next() {
		String string = "";
		long next = this.next;
		do {
			string = string + this.type.charAt((int) (next % this.length));
		} while ((next /= this.length) > 0L);
		++this.next;
		return string;
	}
}
