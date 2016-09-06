package com.allatori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtils {

	public static String read(File file) throws Exception {
		StringBuilder strBuilder = new StringBuilder((int) file.length());
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		int i;
		while ((i = bfReader.read()) != -1) {
			strBuilder.append((char) i);
		}
		bfReader.close();
		return strBuilder.toString();
	}

	public static void method689(File file0, File file1) throws IOException {
		FileInputStream inputStream = new FileInputStream(file0);
		FileOutputStream outputStream = new FileOutputStream(file1);
		FileChannel fileChannel0 = inputStream.getChannel(), fileChannel1 = outputStream.getChannel();
		try {
			MappedByteBuffer mappedByteBuffer = fileChannel0.map(MapMode.READ_ONLY, 0L, fileChannel0.size());
			fileChannel1.write(mappedByteBuffer);
		} finally {
			if (fileChannel0 != null) {
				fileChannel0.close();
			}
			if (fileChannel1 != null) {
				fileChannel1.close();
			}
		}
		inputStream.close();
		outputStream.close();
	}

	public static void write(File file, String toWrite) throws Exception {
		BufferedWriter bfReader = new BufferedWriter(new FileWriter(file));
		bfReader.write(toWrite);
		bfReader.close();
	}
}
