package com.allatori;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

public class LogFile {

	private static ObfuscationHandler obfuscationHandler;

	private static void writeXMLHeader(PrintWriter var0) {
		var0.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		var0.println("<!--\n   " + Info.name() + " " + Info.version() + "\n" + "   Web: " + Info.website() + "\n\n"
				+ "   Log created: " + Calendar.getInstance().getTime() + "\n" + "-->\n");
		var0.println("<allatori>");
	}

	private static void writeXMLEnd(PrintWriter var0) {
		var0.println("</allatori>");
	}

	public static void writeLogFile() {
		String var0;
		if ((var0 = LogUtils.getLogFile()) == null) {
			Logger.printWarning("Log file is not set.");
		} else {
			PrintWriter var1 = null;

			try {
				final FileOutputStream var2 = new FileOutputStream(var0);
				writeXMLHeader(
						var1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(var2, "UTF-8"), 262144)));
				writeMapping(var1, obfuscationHandler);
				writeXMLEnd(var1);
				var1.flush();
			} catch (final FileNotFoundException var15) {
				Logger.printError("Cannot write log to \'" + var0 + "\' file.");
			} catch (final Exception var16) {
				Logger.printError("Writing log file failed with exception: " + var16.getMessage());
			} finally {
				try {
					var1.close();
				} catch (final Exception var14) {
				}

			}

		}
	}

	private static int method1770(Vector<?> var0, String var1) {
		int var2 = 0;
		int var3 = var0.size() - 1;

		for (int var10000 = var2; var10000 <= var3; var10000 = var2) {
			final int var4 = var2 + var3 >> 1;
			final int var6 = ((Comparable<String>) var0.get(var4)).compareTo(var1);
			if (var6 < 0) {
				var2 = var4 + 1;
			} else {
				if (var6 <= 0) {
					return var4;
				}

				var3 = var4 - 1;
			}
		}

		return var2;
	}

	private static void writeMapping(PrintWriter var0, ObfuscationHandler var1) {
		var0.println(" <mapping>");
		final Vector<String> var2 = new Vector<String>(var1.aRenamingMap_512.keySet());
		final Vector<String> var3 = new Vector<String>(var1.aRenamingMap_514.keySet());
		final Vector<String> var4 = new Vector<String>(var1.aRenamingMap_516.keySet());
		Collections.sort(var3);
		Collections.sort(var4);

		int var5;
		int var8;
		String var9;
		String var10;
		String var11;
		int var10000;
		for (var10000 = var5 = var2.size() - 1; var10000 >= 0; var10000 = var5) {
			final String var6 = (String) var2.get(var5);
			var0.println("  <class old=\"" + var6 + "\" new=\"" + var1.aRenamingMap_512.get(var6) + "\">");
			final String var7 = var6 + "&";

			for (var10000 = var8 = method1770(var3, var7); var10000 < var3.size()
					&& (var9 = (String) var3.get(var8)).startsWith(var7); var10000 = var8) {
				var10 = var9.substring(var9.indexOf("&") + 1, var9.lastIndexOf("&"));
				if (!"<init>".equals(var10) && !"<clinit>".equals(var10)) {
					var11 = var9.substring(var9.lastIndexOf("&") + 1);
					var0.println("   <method old=\"" + var10 + var11 + "\" new=\"" + var1.aRenamingMap_514.get(var9)
							+ "\"/>");
					var3.remove(var8);
				} else {
					var3.remove(var8);
				}
			}

			for (var10000 = var8 = method1770(var4, var7); var10000 < var4.size()
					&& (var9 = (String) var4.get(var8)).startsWith(var7); var10000 = var8) {
				var10 = var9.substring(var9.indexOf("&") + 1, var9.lastIndexOf("&"));
				var11 = var9.substring(var9.lastIndexOf("&") + 1);
				var0.println("   <field old=\"" + var10 + " " + var11 + "\" new=\"" + var1.aRenamingMap_516.get(var9)
						+ "\"/>");
				var4.remove(var8);
			}

			var0.println("  </class>");
			--var5;
		}

		Vector<String> var12;
		int var13;
		if ((var12 = new Vector<String>(var1.aRenamingMap_513.keySet())).size() > 0) {
			var0.println("  <annotations>");

			for (var10000 = var13 = var12.size() - 1; var10000 >= 0; var10000 = var13) {
				final String var14 = (String) var12.get(var13);
				var9 = var1.aRenamingMap_513.get(var14);
				var10 = var14.substring(var14.indexOf("&") + 1);
				var11 = var14.substring(0, var14.indexOf("&"));
				var0.println("   <annotation oldClassName=\"" + var11 + "\" oldMethodName=\"" + var10
						+ "\" newMethodName=\"" + var9 + "\"/>");
				--var13;
			}

			var0.println("  </annotations>");
		}

		var0.println("  <sources>");

		for (var10000 = var13 = var1.aVector515.size() - 1; var10000 >= 0; var10000 = var13) {
			final LogRepo var15 = (LogRepo) var1.aVector515.get(var13);
			var0.println("   <source old=\"" + var15.fString + "\" new =\"" + var15.sString + "\"/>");
			--var13;
		}

		var0.println("  </sources>");
		var0.println("  <lines>");

		for (var10000 = var8 = 0; var10000 < var1.aVector511.size(); var10000 = var8) {
			var0.println("   <line l=\"" + var1.aVector511.get(var8) + "\"/>");
			++var8;
		}

		var0.println("  </lines>");
		var0.println(" </mapping>");
	}

	public static void setObfuscationHandler(ObfuscationHandler obfHandler) {
		obfuscationHandler = obfHandler;
	}
}
