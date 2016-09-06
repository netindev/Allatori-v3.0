package com.allatori;

import java.util.Hashtable;
import java.util.Set;

public class RenamingMap {

	private final Hashtable nameTable = new Hashtable();

	public void method440(String var1) {
		this.nameTable.remove(var1);
	}

	public int method441() {
		return this.nameTable.size();
	}

	public String get(String var1) {
		return (String) this.nameTable.get(var1);
	}

	public void put(String var1, String var2) {
		this.nameTable.put(var1, var2);
	}

	public Set method444() {
		return this.nameTable.keySet();
	}

	public boolean containsKey(String var1) {
		return this.nameTable.containsKey(var1);
	}
}
