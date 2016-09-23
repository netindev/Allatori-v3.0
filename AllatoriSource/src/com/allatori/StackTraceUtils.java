package com.allatori;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackTraceUtils {
	
	/* OK */

	public static void translateStackTrace(BufferedReader inputReader, StackTraceLog log, PrintWriter outputWriter) throws IOException {
		final Pattern firstPattern = Pattern.compile("(.*(?:at|Frame)\\s+)(.*)\\((.*)\\)(.*)");
		final Pattern secondPattern = Pattern.compile("(.*syslog:\\s+)(.*?)\\((.*)\\)(.*)");
		String group1 = null, group2 = null, group3 = null, group4 = null;
		String actualLine;
		for (BufferedReader bufferedReader = inputReader; (actualLine = bufferedReader.readLine()) != null; bufferedReader = inputReader) {
			boolean match = false;
			Matcher matcher = firstPattern.matcher(actualLine);
			if (matcher.matches()) {
				match = true;
				group1 = matcher.group(1);
				if ((group2 = matcher.group(2)).indexOf(40) > 0) {
					group2 = group2.substring(0, group2.indexOf(40));
				}
				group3 = matcher.group(3);
				group4 = matcher.group(4);
			}
			if (!match && (matcher = secondPattern.matcher(actualLine)).matches()) {
				match = true;
				group1 = matcher.group(1);
				group2 = matcher.group(2);
				group3 = matcher.group(3);
				group4 = matcher.group(4);
			}
			if (match && log.init.containsKey(group2)) {
				final String iLog = log.init.get(group2);
				boolean compMeth = false;
				if (group3.startsWith("(Compiled Method)")) {
					compMeth = true;
					group3 = group3.substring("(Compiled Method)".length());
				}
				if (group3.indexOf(58) >= 0) {
					String subInd = group3.substring(0, group3.indexOf(58));
					String sub1 = group3.substring(group3.indexOf(58) + 1);
					if (log.oldNewRep.containsKey(subInd)) {
						subInd = log.oldNewRep.get(subInd);
					}
					try {
						int indOf = log.vector.indexOf(Integer.valueOf(sub1));
						if (indOf >= 0) {
							sub1 = "" + indOf;
						}
					} catch (final NumberFormatException e) {
						/* OK */
					}
					group3 = subInd + ":" + sub1;
				} else if (log.oldNewRep.containsKey(group3)) {
					group3 = log.oldNewRep.get(group3);
				}
				actualLine = group1 + iLog + (compMeth ? "(Compiled Method)" : "") + "(" + group3 + ")" + group4;
			}
			outputWriter.println(actualLine);
		}
	}
}
