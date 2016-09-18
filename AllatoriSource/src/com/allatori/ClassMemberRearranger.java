package com.allatori;

import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;

public class ClassMemberRearranger implements ObfuscationType {

	private void arrangeFieldOrder(ClassGen var1) {
		Field[] var2;
		int var3;
		int var10000;
		for (var10000 = var3 = (var2 = var1.getFields()).length - 1; var10000 >= 0; var10000 = var3) {
			var1.removeField(var2[var3]);
			--var3;
		}

		List var4;
		Class34.arrangeOrder(var4 = Arrays.asList(var2));

		int var5;
		for (var10000 = var5 = var4.size() - 1; var10000 >= 0; var10000 = var5) {
			var1.addField((Field) var4.get(var5));
			--var5;
		}

	}

	@Override
	public void execute(ClassGen var1) {
		this.arrangeFieldOrder(var1);
		this.arrangeMethodOrder(var1);
	}

	private void arrangeMethodOrder(ClassGen var1) {
		Method[] var2;
		int var3;
		int var10000;
		for (var10000 = var3 = (var2 = var1.getMethods()).length - 1; var10000 >= 0; var10000 = var3) {
			var1.removeMethod(var2[var3]);
			--var3;
		}

		List var4;
		Class34.arrangeOrder(var4 = Arrays.asList(var2));

		int var5;
		for (var10000 = var5 = var4.size() - 1; var10000 >= 0; var10000 = var5) {
			var1.addMethod(var2[var5]);
			--var5;
		}

	}

	@Override
	public void terminate() {
		/* empty */
	}
}
