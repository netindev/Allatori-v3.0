package com.allatori;

import java.util.Hashtable;
import java.util.Set;

public class RenamingMap {

	/* OK */

	private final Hashtable<String, String> nameTable = new Hashtable<String, String>();

	public void remove(String string) {
		this.nameTable.remove(string);
	}

	public int size() {
		return this.nameTable.size();
	}

	public String get(String string) {
		return this.nameTable.get(string);
	}

	public void put(String key, String value) {
		this.nameTable.put(key, value);
	}

	public Set<String> keySet() {
		return this.nameTable.keySet();
	}

	public boolean containsKey(String string) {
		return this.nameTable.containsKey(string);
	}
}
