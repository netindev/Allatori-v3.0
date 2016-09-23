package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.ElementValue;

public class AnnotationElementValueGen extends ElementValueGen {
	private final AnnotationEntryGen a;

	public AnnotationElementValueGen(AnnotationEntryGen a, ConstantPoolGen cpool) {
		super(64, cpool);
		this.a = a;
	}

	public AnnotationElementValueGen(int type, AnnotationEntryGen annotation, ConstantPoolGen cpool) {
		super(type, cpool);
		if (type != 64)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type annotation can be built with this ctor - type specified: ")
					.append(type).toString());
		a = annotation;
	}

	public AnnotationElementValueGen(AnnotationElementValue value, ConstantPoolGen cpool, boolean copyPoolEntries) {
		super(64, cpool);
		a = new AnnotationEntryGen(value.getAnnotationEntry(), cpool, copyPoolEntries);
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		a.dump(dos);
	}

	@Override
	public String stringifyValue() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public ElementValue getElementValue() {
		return new AnnotationElementValue(type, a.getAnnotation(), cpGen.getConstantPool());
	}

	public AnnotationEntryGen getAnnotation() {
		return a;
	}
}
