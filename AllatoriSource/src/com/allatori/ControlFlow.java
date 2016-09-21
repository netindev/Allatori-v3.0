package com.allatori;

import java.util.Iterator;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

import com.allatori.obfuscate.opt.BranchTransform;

public class ControlFlow implements ObfuscationType {
	
	/* OK */

	@Override
	public void execute(ClassGen classGen) {
		Vector<ControlFlowTransform> vector = new Vector<ControlFlowTransform>();
		vector.add(new BranchTransform());
		vector.add(new SelectBlockTransform());
		vector.add(new LocalVariableTransform());
		Iterator<ControlFlowTransform> tempIterator;
		for (Iterator<ControlFlowTransform> iterator = tempIterator = vector.iterator(); iterator.hasNext(); iterator = tempIterator) {
			((ControlFlowTransform) tempIterator.next()).patch(classGen);
		}
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
