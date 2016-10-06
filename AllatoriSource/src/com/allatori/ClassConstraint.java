package com.allatori;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.MethodGen;

public class ClassConstraint {

	/* OK */

	private String superNamePattern;
	private boolean ifYes1;
	private int accessType;
	private final Vector<FieldConstraint> fieldConstraints = new Vector<FieldConstraint>();
	private String instanceofPattern;
	private String[] interfacePattern;
	private boolean ifYes2;
	private final Vector<MethodConstraint> methodConstraints = new Vector<MethodConstraint>();
	private String namePattern;

	private void parseTemplate(String string) throws TemplateException {
		final Matcher matcher = Pattern
				.compile(
						"(.*?(?:class|interface))\\s+(.+?)(?:\\s+extends\\s+(.+?))?(?:\\s+implements\\s+(.+?))?(?:\\s+instanceof\\s+(.+?))?")
				.matcher(string);
		if (matcher.matches()) {
			final String accessflags = matcher.group(1);
			this.accessType = Class115.parseAccess(accessflags);
			final String className = matcher.group(2);
			this.namePattern = Class115.localParsePaterrn(className);
			final String superName = matcher.group(3);
			this.superNamePattern = Class115.localParsePaterrn(superName);
			final String interfaceClasses = matcher.group(4);
			this.interfacePattern = Class115.interfacePattern(interfaceClasses);
			final String instanceClassName = matcher.group(5);
			this.instanceofPattern = Class115.localParsePaterrn(instanceClassName);
		} else {
			throw new TemplateException("Invalid template.");
		}
	}

	@Override
	public ClassConstraint clone() {
		final ClassConstraint classConstraint = new ClassConstraint();
		classConstraint.ifYes1 = this.ifYes1;
		classConstraint.accessType = this.accessType;
		classConstraint.namePattern = this.namePattern;
		classConstraint.superNamePattern = this.superNamePattern;
		classConstraint.instanceofPattern = this.instanceofPattern;
		classConstraint.interfacePattern = this.interfacePattern;
		return classConstraint;
	}

	public boolean apply(MethodGen methodGen) {
		for (int i = this.methodConstraints.size() - 1; i >= 0; i--) {
			if (this.methodConstraints.get(i).apply(methodGen)) {
				return true;
			}
		}
		return false;
	}

	private ClassConstraint() {
		/* empty */
	}

	public void addMethodConstraint(MethodConstraint methodConstraint) {
		this.methodConstraints.add(methodConstraint);
	}

	public boolean apply(Method method) {
		for (int i = this.methodConstraints.size() - 1; i >= 0; i--) {
			if (this.methodConstraints.get(i).apply(method)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasFieldConstraints() {
		return this.fieldConstraints.size() > 0;
	}

	public boolean apply(ClassStorage classStorage, ClassGen classGen) {
		return Class115.method1399(classGen, this.accessType) && (classGen.getClassName().matches(this.namePattern)
				&& (classGen.getSuperclassName().matches(this.superNamePattern)
						&& (Class115.method1392(classGen.getInterfaceNames(), this.interfacePattern) && Class115
								.method1398(classStorage, classGen.getClassName(), this.instanceofPattern))));
	}

	public void addFieldConstraint(FieldConstraint fieldConstraint) {
		this.fieldConstraints.add(fieldConstraint);
	}

	public boolean getIfYes2() {
		return this.ifYes2;
	}

	public ClassConstraint(String string, boolean ifYes1, boolean isYes2) throws TemplateException {
		this.ifYes1 = ifYes1;
		this.ifYes2 = isYes2;
		this.parseTemplate(string);
	}

	public boolean getIfYes1() {
		return this.ifYes1;
	}

	public boolean apply(Field field) {
		for (int i = this.fieldConstraints.size() - 1; i >= 0; i--) {
			if (this.fieldConstraints.get(i).apply(field)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasMethodConstraints() {
		return this.methodConstraints.size() > 0;
	}
}
