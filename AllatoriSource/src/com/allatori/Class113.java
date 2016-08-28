package com.allatori;

public class Class113 implements Interface22 {

	private long aLong1167 = 0L;

	@Override
	public void method263() {
		this.aLong1167 = 0L;
	}

	@Override
	public String method262() {
		String string = "";
		long var2 = this.aLong1167;

		do {
			final String aString1165 = "abcdefghijklmnopqrstuvwxyz";
			string = string + aString1165.charAt((int) (var2 % 26L));
		} while ((var2 /= 26L) > 0L);

		++this.aLong1167;
		return string;
	}
}
