package com.allatori;

import java.util.Iterator;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

import com.allatori.obfuscate.opt.BranchTransform;

public class ControlFlow implements ObfuscationType {

	@Override
	public void execute(ClassGen var1) {
		Vector<ControlFlowTransform> var2;
		(var2 = new Vector<ControlFlowTransform>()).add(new BranchTransform());
		var2.add(new SelectBlockTransform());
		var2.add(new LocalVariableTransform());

		Iterator<ControlFlowTransform> var3;
		for (Iterator<ControlFlowTransform> var10000 = var3 = var2.iterator(); var10000.hasNext(); var10000 = var3) {
			((ControlFlowTransform) var3.next()).patch(var1);
		}

	}

	@Override
	public void terminate() {
	}
}
