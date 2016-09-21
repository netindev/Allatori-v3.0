package com.allatori;

import java.io.File;
import java.net.URL;
import java.util.Vector;

public class Configurable {

	private static Vector<ConfigRepo> aVector596 = new Vector<ConfigRepo>();
	private static String aString597 = null;
	private static Vector<RenameRepo> aVector598 = new Vector<RenameRepo>();

	public static String method655() {
		return aString597;
	}

	public static void method656(ConfigRepo var0) {
		aVector596.add(var0);
	}

	public static Vector<RenameRepo> method657() {
		return aVector598;
	}

	public static void method658(String var0) {
		aString597 = var0;
	}

	public static void method659(RenameRepo var0) {
		aVector598.add(var0);
	}

	public static Vector<ConfigRepo> method660() {
		return aVector596;
	}

	static void method1266(ClassStorage classStorage) throws ExtractWatermarkException {
		new Watermarking(classStorage, WatermarkUtil.getKey()).createWatermark(WatermarkUtil.getValue());
	}

	public static void parseConfigFile(String fileName) {
		try {
			ParseConfig.parseConfigFile(fileName);
		} catch (final TemplateException var2) {
			if (var2.getCause() == null) {
				Logger.printError("Configuration error. " + var2.getMessage());
			} else {
				Logger.printError("Error reading configuration. " + var2.getCause().getMessage());
			}

			System.exit(-1);
		}

	}

	private static void method1268(ClassStorage var0) throws Exception {
		final Vector<ConfigRepo> var1 = method660();

		int var2;
		int var10000;
		for (var10000 = var2 = 0; var10000 < var1.size(); var10000 = var2) {
			ConfigRepo var3;
			JarUtils.method450((var3 = (ConfigRepo) var1.get(var2)).aString799, var3.aString800, var0);
			++var2;
		}

		if (method655() != null) {
			final String[] var5 = new String[var1.size()];

			int var4;
			for (var10000 = var4 = 0; var10000 < var1.size(); var10000 = var4) {
				var5[var4] = ((ConfigRepo) var1.get(var4)).aString800;
				++var4;
			}

			JarUtils.method446(var5, method655());
		}

	}

	static ClassStorage getClasses() throws Exception {
		final ClassStorage var0 = new ClassStorage(method1272());
		final Vector<ConfigRepo> var1 = method660();

		int var2;
		int var10000;
		for (var10000 = var2 = 0; var10000 < var1.size(); var10000 = var2) {
			ParseClass.parseClass(((ConfigRepo) var1.get(var2)).aString799, var0);
			++var2;
		}

		final Vector<RenameRepo> var3 = method657();

		int var4;
		for (var10000 = var4 = 0; var10000 < var3.size(); var10000 = var4) {
			ClassLoadingUtils.loadClassesFromDir(((RenameRepo) var3.get(var4)).aString811, var0);
			++var4;
		}

		return var0;
	}

	static String printSplash() {
		final String var0 = " ## #   #    ## ### ### ##  ###";
		final String var1 = "# # #   #   # #  #  # # # #  # ";
		final String var2 = "### #   #   ###  #  # # ##   # ";
		final String var3 = "# # ### ### # #  #  ### # # ###";
		final String var4 = "Obfuscation by " + Info.name() + " " + Info.version();
		final String var5 = Info.website();
		int var6 = Math.max(var0.length(), Math.max(var4.length(), var5.length()));
		var6 += 4;
		StringBuffer var7;
		method1271(var7 = new StringBuffer("\n"), '#', var6).append("\n#");
		method1271(var7, ' ', var6 - 2).append("#\n#");
		int var8 = (var6 - var0.length() - 2) / 2;
		int var9 = var6 - var8 - var0.length() - 2;
		method1271(var7, ' ', var8).append(var0);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var1);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var2);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var3);
		method1271(var7, ' ', var9).append("#\n#");
		final String var10 = "                               ";
		final String var11 = "         DEMO VERSION!         ";
		final String var12 = "    NOT FOR COMMERCIAL USE!    ";
		final String var13 = "                               ";
		final String var14 = "Registered version doesn\'t add ";
		final String var15 = "System.out\'s to the obfuscated ";
		final String var16 = "application.                   ";
		method1271(var7, ' ', var8).append(var10);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var11);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var12);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var13);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var14);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var15);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var8).append(var16);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var6 - 2).append("#\n#");
		method1271(var7, ' ', var6 - 2).append("#\n#");
		var8 = (var6 - var4.length() - 2) / 2;
		var9 = var6 - var8 - var4.length() - 2;
		method1271(var7, ' ', var8).append(var4);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var6 - 2).append("#\n#");
		var8 = (var6 - var5.length() - 2) / 2;
		var9 = var6 - var8 - var5.length() - 2;
		method1271(var7, ' ', var8).append(var5);
		method1271(var7, ' ', var9).append("#\n#");
		method1271(var7, ' ', var6 - 2).append("#\n");
		method1271(var7, '#', var6).append("\n");
		return var7.toString();
	}

	private static StringBuffer method1271(StringBuffer var0, char var1, int var2) {
		int var3;
		for (int var10000 = var3 = 0; var10000 < var2; var10000 = var3) {
			var0.append(var1);
			++var3;
		}

		return var0;
	}

	@SuppressWarnings("deprecation")
	private static URLClassLoaderImpl method1272() {
		final Vector<Object> var0 = ConfigFileHandler.method832();

		int var1;
		for (int var10000 = var1 = 0; var10000 < var0.size(); var10000 = var1) {
			try {
				var0.set(var1, (new File((String) var0.get(var1))).toURL());
			} catch (final Exception var3) {
				var3.printStackTrace();
			}

			++var1;
		}

		final URL[] var2 = (URL[]) var0.toArray(new URL[var0.size()]);
		return new URLClassLoaderImpl(var2);
	}

	private static void method1273(ClassStorage var0) throws Exception {
		final Vector<RenameRepo> var1 = method657();

		int var2;
		for (int var10000 = var2 = 0; var10000 < var1.size(); var10000 = var2) {
			RenameRepo var3;
			DumpUtils.dumpClassStorage((var3 = (RenameRepo) var1.get(var2)).aString811, var3.aString812, var0);
			++var2;
		}

	}

	static String extractWatermark(ClassStorage classStorage) throws ExtractWatermarkException {
		return new Watermarking(classStorage, WatermarkUtil.getKey()).extractWatermark();
	}

	static void method1275(ClassStorage var0) throws Exception {
		method1268(var0);
		method1273(var0);
	}
}
