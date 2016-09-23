package com.allatori;

import java.util.Iterator;
import java.util.Vector;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;

import com.allatori.obfuscate.opt.AntiJDTransform;

public class Obfuscator {

	private final ClassStorage classes;

	private void executeRenamer() {
		try {
			(new Renamer(this.classes)).run();
		} catch (final RunException e) {
			try {
				Logger.printWarning("Rerunning obfuscation due to un-renamed packages.");
				new Renamer(this.classes).run();
			} catch (final RunException f) {
				/* empty */
			}
		}
	}

	private void executeTransforms() {
		final Vector<ObfuscationType> transformsVector = new Vector<ObfuscationType>();
		transformsVector.add(new DemoWaterMark());
		transformsVector.add(new Class63(this.classes));
		transformsVector.add(new ExpiryObfuscation(this.classes));
		if (Tuning.rearrangeClassMembers()) {
			transformsVector.add(new ClassMemberRearranger());
		}
		if (Tuning.isStringObfuscationLayer2Enabled()) {
			transformsVector.add(new StringObfuscationLayer1(this.classes));
		}
		if (Tuning.isControlFlowObfuscationEnabled()) {
			transformsVector.add(new ControlFlow());
		}
		transformsVector.add(new AntiJDTransform(this.classes));
		Iterator<?> valuesIterator = this.classes.valuesIterator();
		for (Iterator<?> iterator = valuesIterator; iterator.hasNext(); iterator = valuesIterator) {
			final ClassGen classGen = (ClassGen) valuesIterator.next();
			Iterator<ObfuscationType> transformsIterator = transformsVector.iterator();
			for (iterator = transformsIterator; iterator.hasNext(); iterator = transformsIterator) {
				((ObfuscationType) transformsIterator.next()).execute(classGen);
			}
		}
		Iterator<ObfuscationType> obfIterator;
		for (Iterator<?> iterator = obfIterator = transformsVector.iterator(); iterator.hasNext(); iterator = obfIterator) {
			((ObfuscationType) obfIterator.next()).terminate();
		}
	}

	public Obfuscator(ClassStorage classStorage) {
		this.classes = classStorage;
		Method.setComparator(MethodComparator.instance());
	}

	private void method1525() {
		final Class95 var1 = new Class95();
		Iterator<?> var2;
		for (Iterator<?> var10000 = var2 = this.classes.valuesIterator(); var10000.hasNext(); var10000 = var2) {
			var1.method1258((ClassGen) var2.next());
		}
	}

	public void method1526() {
		Iterator<?> var1;
		Iterator<?> var10000;
		for (var10000 = var1 = this.classes.valuesIterator(); var10000.hasNext(); var10000 = var1) {
			if (((ClassGen) var1.next()).getSuperclassName().startsWith("javax.microedition.")) {
				Tuning.setIsWeakStringEncryption();
				break;
			}
		}
		this.executeTransforms();
		this.executeRenamer();
		if (Tuning.isStringObfuscationLayer2Enabled()) {
			final StringObfuscationLayer2 var2 = new StringObfuscationLayer2();

			Iterator<?> var3;
			for (var10000 = var3 = this.classes.valuesIterator(); var10000.hasNext(); var10000 = var3) {
				final ClassGen var4 = (ClassGen) var3.next();
				var2.execute(var4);
			}
		}
		this.method1525();
	}
}
