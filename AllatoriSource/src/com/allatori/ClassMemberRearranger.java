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
		final Field[] fields = classGen.getFields();
		for (int i = fields.length - 1; i >= 0; i--) {
			classGen.removeField(fields[i]);
		}
		final List list = Arrays.asList(fields);
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
		final Method[] methods = classGen.getMethods();
		for (int i = methods.length - 1; i >= 0; i--) {
			classGen.removeMethod(methods[i]);
		}
		final List list = Arrays.asList(methods);
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
