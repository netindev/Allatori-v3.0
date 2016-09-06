package org.apache.bcel.classfile;

import java.io.Serializable;
import java.util.Stack;

public class DescendingVisitor implements Visitor {
	private final JavaClass clazz;
	private final Visitor visitor;
	private final Stack<Serializable> stack = new Stack<Serializable>();

	public Object predecessor() {
		return predecessor(0);
	}

	public Object predecessor(int level) {
		final int size = stack.size();
		if (size < 2 || level < 0)
			return null;
		return stack.elementAt(size - (level + 2));
	}

	public Object current() {
		return stack.peek();
	}

	public DescendingVisitor(JavaClass clazz, Visitor visitor) {
		this.clazz = clazz;
		this.visitor = visitor;
	}

	public void visit() {
		clazz.accept(this);
	}

	@Override
	public void visitJavaClass(JavaClass _clazz) {
		stack.push(_clazz);
		_clazz.accept(visitor);
		final Field[] fields = _clazz.getFields();
		for (int i = 0; i < fields.length; i++)
			fields[i].accept(this);
		final Method[] methods = _clazz.getMethods();
		for (int i = 0; i < methods.length; i++)
			methods[i].accept(this);
		final Attribute[] attributes = _clazz.getAttributes();
		for (int i = 0; i < attributes.length; i++)
			attributes[i].accept(this);
		_clazz.getConstantPool().accept(this);
		stack.pop();
	}

	@Override
	public void visitAnnotation(Annotations annotation) {
		stack.push(annotation);
		annotation.accept(visitor);
		final AnnotationEntry[] entries = annotation.getAnnotationEntries();
		for (int i = 0; i < entries.length; i++)
			entries[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitAnnotationEntry(AnnotationEntry annotationEntry) {
		stack.push(annotationEntry);
		annotationEntry.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitField(Field field) {
		stack.push(field);
		field.accept(visitor);
		final Attribute[] attributes = field.getAttributes();
		for (int i = 0; i < attributes.length; i++)
			attributes[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitConstantValue(ConstantValue cv) {
		stack.push(cv);
		cv.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitMethod(Method method) {
		stack.push(method);
		method.accept(visitor);
		final Attribute[] attributes = method.getAttributes();
		for (int i = 0; i < attributes.length; i++)
			attributes[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitExceptionTable(ExceptionTable table) {
		stack.push(table);
		table.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitCode(Code code) {
		stack.push(code);
		code.accept(visitor);
		final CodeException[] table = code.getExceptionTable();
		for (int i = 0; i < table.length; i++)
			table[i].accept(this);
		final Attribute[] attributes = code.getAttributes();
		for (int i = 0; i < attributes.length; i++)
			attributes[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitCodeException(CodeException ce) {
		stack.push(ce);
		ce.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitLineNumberTable(LineNumberTable table) {
		stack.push(table);
		table.accept(visitor);
		final LineNumber[] numbers = table.getLineNumberTable();
		for (int i = 0; i < numbers.length; i++)
			numbers[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitLineNumber(LineNumber number) {
		stack.push(number);
		number.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitLocalVariableTable(LocalVariableTable table) {
		stack.push(table);
		table.accept(visitor);
		final LocalVariable[] vars = table.getLocalVariableTable();
		for (int i = 0; i < vars.length; i++)
			vars[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitStackMap(StackMap table) {
		stack.push(table);
		table.accept(visitor);
		final StackMapEntry[] vars = table.getStackMap();
		for (int i = 0; i < vars.length; i++)
			vars[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitStackMapEntry(StackMapEntry var) {
		stack.push(var);
		var.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitStackMapTable(StackMapTable table) {
		stack.push(table);
		table.accept(visitor);
		final StackMapTableEntry[] vars = table.getStackMapTable();
		for (int i = 0; i < vars.length; i++)
			vars[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitStackMapTableEntry(StackMapTableEntry var) {
		stack.push(var);
		var.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitLocalVariable(LocalVariable var) {
		stack.push(var);
		var.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantPool(ConstantPool cp) {
		stack.push(cp);
		cp.accept(visitor);
		final Constant[] constants = cp.getConstantPool();
		for (int i = 1; i < constants.length; i++) {
			if (constants[i] != null)
				constants[i].accept(this);
		}
		stack.pop();
	}

	@Override
	public void visitConstantClass(ConstantClass constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantDouble(ConstantDouble constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantFieldref(ConstantFieldref constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantFloat(ConstantFloat constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantInteger(ConstantInteger constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantLong(ConstantLong constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantMethodref(ConstantMethodref constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantNameAndType(ConstantNameAndType constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantString(ConstantString constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitConstantUtf8(ConstantUtf8 constant) {
		stack.push(constant);
		constant.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitInnerClasses(InnerClasses ic) {
		stack.push(ic);
		ic.accept(visitor);
		final InnerClass[] ics = ic.getInnerClasses();
		for (int i = 0; i < ics.length; i++)
			ics[i].accept(this);
		stack.pop();
	}

	@Override
	public void visitInnerClass(InnerClass inner) {
		stack.push(inner);
		inner.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitDeprecated(Deprecated attribute) {
		stack.push(attribute);
		attribute.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitSignature(Signature attribute) {
		stack.push(attribute);
		attribute.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitSourceFile(SourceFile attribute) {
		stack.push(attribute);
		attribute.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitSynthetic(Synthetic attribute) {
		stack.push(attribute);
		attribute.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitUnknown(Unknown attribute) {
		stack.push(attribute);
		attribute.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitAnnotationDefault(AnnotationDefault obj) {
		stack.push(obj);
		obj.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitEnclosingMethod(EnclosingMethod obj) {
		stack.push(obj);
		obj.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitLocalVariableTypeTable(LocalVariableTypeTable obj) {
		stack.push(obj);
		obj.accept(visitor);
		stack.pop();
	}

	@Override
	public void visitParameterAnnotation(ParameterAnnotations obj) {
		stack.push(obj);
		obj.accept(visitor);
		stack.pop();
	}
}
