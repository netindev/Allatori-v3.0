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

	public static void method689(File var0, File var1) throws IOException {
		FileChannel var2 = null;
		FileChannel var3 = null;
		try {
			var2 = (new FileInputStream(var0)).getChannel();
			var3 = (new FileOutputStream(var1)).getChannel();
			final MappedByteBuffer var4 = var2.map(MapMode.READ_ONLY, 0L, var2.size());
			var3.write(var4);
		} finally {
			if (var2 != null) {
				var2.close();
			}
			if (var3 != null) {
				var3.close();
			}
		}
	}

	public static void write(File file, String toWrite) throws Exception {
		BufferedWriter bfReader = new BufferedWriter(new FileWriter(file));
		bfReader.write(toWrite);
		bfReader.close();
	}
}
