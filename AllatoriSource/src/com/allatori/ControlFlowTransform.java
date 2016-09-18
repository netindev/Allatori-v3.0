package com.allatori;

import org.apache.bcel.generic.ClassGen;

public interface ControlFlowTransform {

	/* OK */
	
	void patch(ClassGen classGen);
}
