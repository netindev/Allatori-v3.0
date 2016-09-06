package com.allatori;

import org.apache.bcel.generic.ClassGen;

public interface ObfuscationType {

	void execute(ClassGen var1);

	void terminate();
}
