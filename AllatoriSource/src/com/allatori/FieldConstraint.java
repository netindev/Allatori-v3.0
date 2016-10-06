package com.allatori;

import org.apache.bcel.classfile.Field;

public class FieldConstraint {

	/* OK */

	private int parseAccess;
	private String pattF;
	private String parsePattern;

	private void parseTemplate(String string) throws TemplateException {
		final String[] split = string.split("\\s+");
		if (split != null && split.length != 0) {
			this.parsePattern = Class115.parsePattern(split[split.length - 1]);
			if (split.length > 1 && !Class115.method1389(split[split.length - 2])) {
				this.pattF = Class115.localParsePaterrn(split[split.length - 2]);
			} else {
				this.pattF = ".*";
			}
			String build = "";
			for (int i = split.length - 3; i >= 0; i--) {
				build = build + " " + split[i];
			}
			if (split.length >= 2 && Class115.method1389(split[split.length - 2])) {
				build = build + " " + split[split.length - 2];
			}
			if (build.equals("")) {
				build = "*";
			}
			this.parseAccess = Class115.parseAccess(build);
		} else {
			throw new TemplateException("Invalid template.");
		}
	}

	public FieldConstraint(String string) throws TemplateException {
		this.parseTemplate(string);
	}

	public boolean apply(Field field) {
		return !Class115.method1399(field, this.parseAccess) ? false
				: (!field.getName().matches(this.parsePattern) ? false
						: field.getType().toString().matches(this.pattF));
	}
}
