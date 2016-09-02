package com.allatori;

import java.util.Iterator;
import java.util.Vector;

import org.apache.bcel.generic.ClassGen;

import com.allatori.obfuscate.opt.BranchTransform;

public class ControlFlow implements ObfuscationType {
	
	/* OK */

	@Override
	public void execute(ClassGen cg) {
		Vector<ControlFlowTransform> vector = new Vector<ControlFlowTransform>();
		vector.add(new BranchTransform());
		vector.add(new SelectBlockTransform());
		vector.add(new LocalVariableTransform());
		
		for (Iterator<ControlFlowTransform> iterator = vector.iterator(); iterator.hasNext();) {
			((ControlFlowTransform) iterator.next()).patch(cg);
		}
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
