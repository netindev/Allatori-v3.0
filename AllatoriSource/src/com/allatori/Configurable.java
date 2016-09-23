package com.allatori;

import java.io.File;
import java.net.URL;
import java.util.Vector;

public class Configurable {

	private static Vector<ConfigRepo> configRepo = new Vector<ConfigRepo>();
	private static String file = null;
	private static Vector<RenameRepo> renameRepo = new Vector<RenameRepo>();

	public static String getFile() {
		return file;
	}

	public static void addConfigRepo(ConfigRepo config) {
		configRepo.add(config);
	}

	public static Vector<RenameRepo> getRenameRepo() {
		return renameRepo;
	}

	public static void setFile(String string) {
		file = string;
	}

	public static void addRenameRepo(RenameRepo rename) {
		renameRepo.add(rename);
	}

	public static Vector<ConfigRepo> getConfigRepo() {
		return configRepo;
	}

	static void createWatermark(ClassStorage classStorage) throws ExtractWatermarkException {
		new Watermarking(classStorage, WatermarkUtil.getExtract()).createWatermark(WatermarkUtil.getValue());
	}

	public static void parseConfigFile(String fileName) {
		try {
			ParseConfig.parseConfigFile(fileName);
		} catch (final TemplateException e) {
			if (e.getCause() == null) {
				Logger.printError("Configuration error. " + e.getMessage());
			} else {
				Logger.printError("Error reading configuration. " + e.getCause().getMessage());
			}
			System.exit(-1);
		}
	}

	private static void method1268(ClassStorage var0) throws Exception {
		final Vector<ConfigRepo> var1 = getConfigRepo();

		int var2;
		int var10000;
		for (var10000 = var2 = 0; var10000 < var1.size(); var10000 = var2) {
			ConfigRepo var3;
			JarUtils.method450((var3 = (ConfigRepo) var1.get(var2)).key, var3.value, var0);
			++var2;
		}

		if (getFile() != null) {
			final String[] var5 = new String[var1.size()];

			int var4;
			for (var10000 = var4 = 0; var10000 < var1.size(); var10000 = var4) {
				var5[var4] = ((ConfigRepo) var1.get(var4)).value;
				++var4;
			}

			JarUtils.method446(var5, getFile());
		}

	}

	static ClassStorage getClasses() throws Exception {
		final ClassStorage classStorage = new ClassStorage(method1272());
		final Vector<ConfigRepo> configRepo = getConfigRepo();
		for (int i = 0; i < configRepo.size(); i++) {
			ParseClass.parseClass(((ConfigRepo) configRepo.get(i)).key, classStorage);
		}
		final Vector<RenameRepo> renameRepo = getRenameRepo();
		for (int i = 0; i < renameRepo.size(); i++) {
			ClassLoadingUtils.loadClassesFromDir(((RenameRepo) renameRepo.get(i)).key, classStorage);
		}
		return classStorage;
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
		append(var7 = new StringBuffer("\n"), '#', var6).append("\n#");
		append(var7, ' ', var6 - 2).append("#\n#");
		int var8 = (var6 - var0.length() - 2) / 2;
		int var9 = var6 - var8 - var0.length() - 2;
		append(var7, ' ', var8).append(var0);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var1);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var2);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var3);
		append(var7, ' ', var9).append("#\n#");
		final String var10 = "                               ";
		final String var11 = "         DEMO VERSION!         ";
		final String var12 = "    NOT FOR COMMERCIAL USE!    ";
		final String var13 = "                               ";
		final String var14 = "Registered version doesn\'t add ";
		final String var15 = "System.out\'s to the obfuscated ";
		final String var16 = "application.                   ";
		append(var7, ' ', var8).append(var10);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var11);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var12);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var13);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var14);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var15);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var8).append(var16);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var6 - 2).append("#\n#");
		append(var7, ' ', var6 - 2).append("#\n#");
		var8 = (var6 - var4.length() - 2) / 2;
		var9 = var6 - var8 - var4.length() - 2;
		append(var7, ' ', var8).append(var4);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var6 - 2).append("#\n#");
		var8 = (var6 - var5.length() - 2) / 2;
		var9 = var6 - var8 - var5.length() - 2;
		append(var7, ' ', var8).append(var5);
		append(var7, ' ', var9).append("#\n#");
		append(var7, ' ', var6 - 2).append("#\n");
		append(var7, '#', var6).append("\n");
		return var7.toString();
	}

	private static StringBuffer append(StringBuffer stringBuffer, char toAppend, int max) {
		for (int i = 0; i < max; i++) {
			stringBuffer.append(toAppend);
		}
		return stringBuffer;
	}

	@SuppressWarnings("deprecation")
	private static URLClassLoaderImpl method1272() {
		final Vector<Object> var0 = ConfigFileHandler.getVector();

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

	private static void dumpClass(ClassStorage classStorage) throws Exception {
		final Vector<RenameRepo> vector = getRenameRepo();
		for (int i = 0; i < vector.size(); i++) {
			RenameRepo renameRepo = (RenameRepo) vector.get(i);
			DumpUtils.dumpClassStorage(renameRepo.key, renameRepo.value, classStorage);
		}
	}

	static String extractWatermark(ClassStorage classStorage) throws ExtractWatermarkException {
		return new Watermarking(classStorage, WatermarkUtil.getExtract()).extractWatermark();
	}

	static void method1275(ClassStorage var0) throws Exception {
		method1268(var0);
		dumpClass(var0);
	}
}
