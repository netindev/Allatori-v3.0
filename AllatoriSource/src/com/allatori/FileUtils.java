package com.allatori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtils {

	/* OK */

	public static String readFile(File file) throws Exception {
		final StringBuilder stringBuilder = new StringBuilder((int) file.length());
		final BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "UTF-8"));
		int read;
		while ((read = bufferedReader.read()) != -1) {
			stringBuilder.append((char) read);
		}
		bufferedReader.close();
		return stringBuilder.toString();
	}

	@SuppressWarnings("resource")
	public static void writeMap(File fileF, File fileS) throws IOException {
		final FileChannel fileChannelF = new FileInputStream(fileF).getChannel();
		final FileChannel fileChannelS = new FileOutputStream(fileS).getChannel();
		try {
			final MappedByteBuffer mappedByteBuffer = fileChannelF.map(MapMode.READ_ONLY, 0L, fileChannelF.size());
			fileChannelS.write(mappedByteBuffer);
		} finally {
			if (fileChannelF != null) {
				fileChannelF.close();
			}
			if (fileChannelS != null) {
				fileChannelS.close();
			}
		}
	}

	public static void writeFile(File file, String write) throws Exception {
		final BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		bufferedWriter.write(write);
		bufferedWriter.close();
	}
}
