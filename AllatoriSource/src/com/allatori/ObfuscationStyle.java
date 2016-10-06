package com.allatori;

public class ObfuscationStyle implements IObfuscationStyle {

	/* OK */

	private final ObfuscationStyleMixed obfStyleMix = new ObfuscationStyleMixed();
	private int count = 0;
	private final String[] stringArr;

	@Override
	public String next() {
		return this.count < this.stringArr.length ? this.stringArr[this.count++] : this.obfStyleMix.next();
	}

	public ObfuscationStyle(String[] args) {
		this.stringArr = args;
	}

	@Override
	public void reset() {
		this.count = 0;
		this.obfStyleMix.reset();
	}
}
