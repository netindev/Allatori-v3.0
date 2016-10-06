package com.allatori;

public class ObfuscationStyleLow implements IObfuscationStyle {

	/* OK */

	private final String type = "abcdefghijklmnopqrstuvwxyz";
	private long next = 0L;

	@Override
	public void reset() {
		this.next = 0L;
	}

	@Override
	public String next() {
		String string = "";
		long next = this.next;
		do {
			string = string + this.type.charAt((int) (next % 26L));
		} while ((next /= 26L) > 0L);
		++this.next;
		return string;
	}
}
