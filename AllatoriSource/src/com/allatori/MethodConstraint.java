package com.allatori;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.MethodGen;

public class MethodConstraint {
	
	/* OK */

	private String parsePattern;
	private String replace;
	private int parseAccess;
	private String pattF;

	public boolean apply(Method method) {
		return !Class115.method1399(method, this.parseAccess) ? false
				: (!method.getName().matches(this.parsePattern) ? false
						: (!method.getReturnType().toString().matches(this.pattF) ? false
								: Class115.method1388(method.getArgumentTypes(), this.replace)));
	}

	private void parseTemplate(String string) throws TemplateException {
		String[] split = string.split("\\(|\\)");
		if (split.length != 2 && (split.length != 1 || !string.endsWith("()"))) {
			throw new TemplateException("Invalid template.");
		} else {
			if (split.length == 1) {
				this.replace = "";
			} else {
				final String getSecond = split[1];
				this.replace = Class115.replaceAll(getSecond);
			}
			String[] splitS = (string = split[0]).split("\\s+");
			if (splitS != null && splitS.length != 0) {
				this.parsePattern = Class115.parsePattern(splitS[splitS.length - 1]);
				if (splitS.length > 1 && !Class115.method1389(splitS[splitS.length - 2])) {
					this.pattF = Class115.localParsePattern(splitS[splitS.length - 2]);
				} else {
					this.pattF = ".*";
				}
				String build = "";
				for (int i = splitS.length - 3; i >= 0; i--) {
					build = build + " " + splitS[i];
				}
				if (splitS.length >= 2 && Class115.method1389(splitS[splitS.length - 2])) {
					build = build + " " + splitS[splitS.length - 2];
				}
				if (build.equals("")) {
					build = "*";
				}
				this.parseAccess = Class115.parseAccess(build);
			} else {
				throw new TemplateException("Invalid template.");
			}
		}
	}

	public MethodConstraint(String string) throws TemplateException {
		this.parseTemplate(string);
	}

	public boolean apply(MethodGen methodGen) {
		return !Class115.method1399(methodGen, this.parseAccess) ? false
				: (!methodGen.getName().matches(this.parsePattern) ? false
						: (!methodGen.getReturnType().toString().matches(this.pattF) ? false
								: Class115.method1388(methodGen.getArgumentTypes(), this.replace)));
	}
}
