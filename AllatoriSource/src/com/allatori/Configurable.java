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

	private static void processJar(ClassStorage classStorage) throws Exception {
		final Vector<ConfigRepo> vector = getConfigRepo();
		for (int i = 0; i < vector.size(); i++) {
			ConfigRepo configRepo = vector.get(i);
			JarUtils.method450(configRepo.key, configRepo.value, classStorage);
		}
		if (getFile() != null) {
			final String[] stringArr = new String[vector.size()];
			for (int i = 0; i < vector.size(); i++) {
				stringArr[i] = vector.get(i).value;
			}
			JarUtils.readAndPut(stringArr, getFile());
		}
	}

	static ClassStorage getClasses() throws Exception {
		final ClassStorage classStorage = new ClassStorage(getFilesURL());
		final Vector<ConfigRepo> configRepo = getConfigRepo();
		for (int i = 0; i < configRepo.size(); i++) {
			ParseClass.parseClass(configRepo.get(i).key, classStorage);
		}
		final Vector<RenameRepo> renameRepo = getRenameRepo();
		for (int i = 0; i < renameRepo.size(); i++) {
			ClassLoadingUtils.loadClassesFromDir(renameRepo.get(i).key, classStorage);
		}
		return classStorage;
	}

	static String printSplash() {
		final String fLine = " ## #   #    ## ### ### ##  ###";
		final String sLine = "# # #   #   # #  #  # # # #  # ";
		final String tLine = "### #   #   ###  #  # # ##   # ";
		final String pLine = "# # ### ### # #  #  ### # # ###";
		final String credits = "Obfuscation by " + Info.name() + " " + Info.version();
		final String webiste = Info.website();
		int max = Math.max(fLine.length(), Math.max(credits.length(), webiste.length()));
		max += 4;
		StringBuffer stringBuffer = new StringBuffer("\n");
		append(stringBuffer, '#', max).append("\n#");
		append(stringBuffer, ' ', max - 2).append("#\n#");
		int lM2 = (max - fLine.length() - 2) / 2;
		int lM4 = max - lM2 - fLine.length() - 2;
		append(stringBuffer, ' ', lM2).append(fLine);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(sLine);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(tLine);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(pLine);
		append(stringBuffer, ' ', lM4).append("#\n#");
		final String demo0 = "                               ";
		final String demo1 = "         DEMO VERSION!         ";
		final String demo2 = "    NOT FOR COMMERCIAL USE!    ";
		final String demo3 = "                               ";
		final String demo4 = "Registered version doesn\'t add ";
		final String demo5 = "System.out\'s to the obfuscated ";
		final String demo6 = "application.                   ";
		append(stringBuffer, ' ', lM2).append(demo0);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(demo1);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(demo2);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(demo3);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(demo4);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(demo5);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', lM2).append(demo6);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', max - 2).append("#\n#");
		append(stringBuffer, ' ', max - 2).append("#\n#");
		lM2 = (max - credits.length() - 2) / 2;
		lM4 = max - lM2 - credits.length() - 2;
		append(stringBuffer, ' ', lM2).append(credits);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', max - 2).append("#\n#");
		lM2 = (max - webiste.length() - 2) / 2;
		lM4 = max - lM2 - webiste.length() - 2;
		append(stringBuffer, ' ', lM2).append(webiste);
		append(stringBuffer, ' ', lM4).append("#\n#");
		append(stringBuffer, ' ', max - 2).append("#\n");
		append(stringBuffer, '#', max).append("\n");
		return stringBuffer.toString();
	}

	private static StringBuffer append(StringBuffer stringBuffer, char toAppend, int max) {
		for (int i = 0; i < max; i++) {
			stringBuffer.append(toAppend);
		}
		return stringBuffer;
	}

	@SuppressWarnings("deprecation")
	private static URLClassLoaderImpl getFilesURL() {
		final Vector<Object> vector = ConfigFileHandler.getVector();
		for (int i = 0; i < vector.size(); i++) {
			try {
				vector.set(i, (new File((String) vector.get(i))).toURL());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		final URL[] urlArr = vector.toArray(new URL[vector.size()]);
		return new URLClassLoaderImpl(urlArr);
	}

	private static void dumpClass(ClassStorage classStorage) throws Exception {
		final Vector<RenameRepo> vector = getRenameRepo();
		for (int i = 0; i < vector.size(); i++) {
			final RenameRepo renameRepo = vector.get(i);
			DumpUtils.dumpClassStorage(renameRepo.key, renameRepo.value, classStorage);
		}
	}
	
	static void createWatermark(ClassStorage classStorage) throws ExtractWatermarkException {
		new Watermarking(classStorage, WatermarkUtil.getExtract()).createWatermark(WatermarkUtil.getValue());
	}

	static String extractWatermark(ClassStorage classStorage) throws ExtractWatermarkException {
		return new Watermarking(classStorage, WatermarkUtil.getExtract()).extractWatermark();
	}

	static void start(ClassStorage classStorage) throws Exception {
		processJar(classStorage);
		dumpClass(classStorage);
	}
}
