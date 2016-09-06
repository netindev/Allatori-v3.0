package com.allatori;

import java.util.Vector;

public class Class72 {

	private static Vector<Object> vector = new Vector<Object>();

	public static Vector<Object> getVector() {
		return vector;
	}

	public static void add(String string) {
		vector.add(string);
	}

}
