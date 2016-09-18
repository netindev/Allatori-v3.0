package com.allatori;

import org.xml.sax.Locator;

public class LocatorImpl implements Locator {

	public LocatorImpl(ConfigFileHandler configFileHandler) {}

	@Override
	public int getLineNumber() {
		return -1;
	}

	@Override
	public String getSystemId() {
		return null;
	}

	@Override
	public String getPublicId() {
		return null;
	}

	@Override
	public int getColumnNumber() {
		return -1;
	}
}
