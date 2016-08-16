package com.allatori;

import org.xml.sax.Locator;

public class LocatorImpl implements Locator {


    public LocatorImpl(ConfigFileHandler var1) {
        ConfigFileHandler configFileHandler = var1;
    }

    public int getLineNumber() {
        return -1;
    }

    public String getSystemId() {
        return null;
    }

    public String getPublicId() {
        return null;
    }

    public int getColumnNumber() {
        return -1;
    }
}
