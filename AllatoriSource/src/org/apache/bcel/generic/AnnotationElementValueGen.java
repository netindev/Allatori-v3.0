package org.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.ElementValue;

public class AnnotationElementValueGen extends ElementValueGen {
	private AnnotationEntryGen annotationEntryGen;

	public AnnotationElementValueGen(AnnotationEntryGen annotationEntryGen, ConstantPoolGen cpool) {
		super(64, cpool);
		this.annotationEntryGen = annotationEntryGen;
	}

	public AnnotationElementValueGen(int type, AnnotationEntryGen annotation, ConstantPoolGen cpool) {
		super(type, cpool);
		if (type != 64)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type annotation can be built with this ctor - type specified: ")
					.append(type).toString());
		annotationEntryGen = annotation;
	}

	public AnnotationElementValueGen(AnnotationElementValue value, ConstantPoolGen cpool, boolean copyPoolEntries) {
		super(64, cpool);
		annotationEntryGen = new AnnotationEntryGen(value.getAnnotationEntry(), cpool, copyPoolEntries);
	}

	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		annotationEntryGen.dump(dos);
	}

	public String stringifyValue() {
		throw new RuntimeException("Not implemented yet");
	}

	public ElementValue getElementValue() {
		return new AnnotationElementValue(type, annotationEntryGen.getAnnotation(), cpGen.getConstantPool());
	}

	public AnnotationEntryGen getAnnotation() {
		return annotationEntryGen;
	}
}
