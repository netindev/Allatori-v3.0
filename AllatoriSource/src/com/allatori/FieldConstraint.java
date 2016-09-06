package com.allatori;

import org.apache.bcel.classfile.Field;

public class FieldConstraint {

	private int anInt736;
	private String aString737;
	private String aString738;

	private void method1404(String var1) throws TemplateException {
		String[] var2;
		if ((var2 = var1.split("\\s+")) != null && var2.length != 0) {
			this.aString738 = Class115.parsePatt(var2[var2.length - 1]);
			if (var2.length > 1 && !Class115.method1389(var2[var2.length - 2])) {
				this.aString737 = Class115.parsePattern(var2[var2.length - 2]);
			} else {
				this.aString737 = ".*";
			}

			String var3 = "";

			int var4;
			for (int var10000 = var4 = var2.length - 3; var10000 >= 0; var10000 = var4) {
				var3 = var3 + " " + var2[var4];
				--var4;
			}

			if (var2.length >= 2 && Class115.method1389(var2[var2.length - 2])) {
				var3 = var3 + " " + var2[var2.length - 2];
			}

			if (var3.equals("")) {
				var3 = "*";
			}

			this.anInt736 = Class115.parseAccess(var3);
		} else {
			throw new TemplateException("Invalid template.");
		}
	}

	public FieldConstraint(String var1) throws TemplateException {
		this.method1404(var1);
	}

	public boolean apply(Field var1) {
		return !Class115.method1399(var1, this.anInt736) ? false
				: (!var1.getName().matches(this.aString738) ? false
						: var1.getType().toString().matches(this.aString737));
	}
}
