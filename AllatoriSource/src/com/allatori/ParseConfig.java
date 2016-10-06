package com.allatori;

public class ParseConfig {

	/* OK */

	public static void parseConfigFile(String string) throws TemplateException {
		new ConfigFile(string).parse();
	}
}
