package com.allatori;

import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;

public class ClassMemberRearranger implements ObfuscationType {
	
	/* OK */

	private void arrangeFieldOrder(ClassGen cg) {
		Field[] fields = cg.getFields();
		for (int i = fields.length - 1; i >= 0; i--) {
			cg.removeField(fields[i]);
		}
		List<Field> list = Arrays.asList(fields);
		Class34.arrangeOrder(list);
		for (int i = list.size() - 1; i >= 0; i--) {
			cg.addField(list.get(i));
		}
	}
	
	private void arrangeMethodOrder(ClassGen cg) {
		Method[] methods = cg.getMethods();
		for (int i = methods.length - 1; i >= 0; i--) {
			cg.removeMethod(methods[i]);
		}
		List<?> list = Arrays.asList(methods);
		Class34.arrangeOrder(list);
		for (int i = list.size() - 1; i >= 0; i--) {
			cg.addMethod(methods[i]);
		}
	}

	@Override
	public void execute(ClassGen cg) {
		this.arrangeFieldOrder(cg);
		this.arrangeMethodOrder(cg);
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
