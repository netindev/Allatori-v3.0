package com.allatori;

public class ParseConfig {

	public static void parseConfigFile(String var0) throws TemplateException {
		new ConfigFile(var0).parse();
	}
}
