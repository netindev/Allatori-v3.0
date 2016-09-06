package com.allatori;

import org.apache.bcel.generic.ClassGen;

public interface ControlFlowTransform {

    void patch(ClassGen var1);
}
