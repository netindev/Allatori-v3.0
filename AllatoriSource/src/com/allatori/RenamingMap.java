package com.allatori;

import java.util.Hashtable;
import java.util.Set;

public class RenamingMap {
	
	/* OK */

	private final Hashtable<String, String> nameTable = new Hashtable<String, String>();

	public int size() {
		return this.nameTable.size();
	}

	public String get(String str) {
		return this.nameTable.get(str);
	}

	public void put(String str0, String str1) {
		this.nameTable.put(str0, str1);
	}

	public Set<String> keySet() {
		return this.nameTable.keySet();
	}

	public boolean containsKey(String str) {
		return this.nameTable.containsKey(str);
	}
}
