package com.allatori;

import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClassMemberRearranger implements ObfuscationType {
	
	/* OK */

	private void arrangeFieldOrder(ClassGen classGen) {
		Field[] fields = classGen.getFields();
		for (int i = fields.length - 1; i >= 0; i--) {
			classGen.removeField(fields[i]);
		}
		List list = Arrays.asList(fields);
		Class34.arrangeOrder(list);
		for (int i = list.size() - 1; i >= 0; i--) {
			classGen.addField((Field) list.get(i));
			--i;
		}
	}

	@Override
	public void execute(ClassGen classGen) {
		this.arrangeFieldOrder(classGen);
		this.arrangeMethodOrder(classGen);
	}

	private void arrangeMethodOrder(ClassGen classGen) {
		Method[] methods = classGen.getMethods();
		for (int i = methods.length - 1; i >= 0; i--) {
			classGen.removeMethod(methods[i]);
		}
		List list = Arrays.asList(methods);
		Class34.arrangeOrder(list);
		for (int i = list.size() - 1; i >= 0; i--) {
			classGen.addMethod(methods[i]);
		}
	}

	@Override
	public void terminate() {
		/* empty */
	}
}
