package com.allatori;

public class ObfuscationStyleLowCase implements IObfStyle {
	
	/* OK */

	private long next = 0L;

	@Override
	public void resetNext() {
		this.next = 0L;
	}

	@Override
	public String nextChar() {
		String toReturn = "";
		long next = this.next;
		do {
			final String lowerCase = "abcdefghijklmnopqrstuvwxyz";
			toReturn = toReturn + lowerCase.charAt((int) (next % 26L));
		} while ((next /= 26L) > 0L);
		++this.next;
		return toReturn;
	}
}
