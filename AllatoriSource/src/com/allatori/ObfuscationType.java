package com.allatori;

import org.apache.bcel.generic.ClassGen;

public interface ObfuscationType {

	/* OK */

	void execute(ClassGen classGen);

	void terminate();
}
