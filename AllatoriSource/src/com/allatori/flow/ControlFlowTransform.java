package com.allatori.flow;

import org.apache.bcel.generic.ClassGen;

public interface ControlFlowTransform {

    void patch(ClassGen var1);
}
