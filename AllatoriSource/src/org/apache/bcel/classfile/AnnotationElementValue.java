package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class AnnotationElementValue extends ElementValue {
	private final AnnotationEntry annotationEntry;

	public AnnotationElementValue(int type, AnnotationEntry annotationEntry, ConstantPool cpool) {
		super(type, cpool);
		if (type != 64)
			throw new RuntimeException(new StringBuilder()
					.append("Only element values of type annotation can be built with this ctor - type specified: ")
					.append(type).toString());
		this.annotationEntry = annotationEntry;
	}

	@Override
	public void dump(DataOutputStream dos) throws IOException {
		dos.writeByte(type);
		annotationEntry.dump(dos);
	}

	@Override
	public String stringifyValue() {
		return annotationEntry.toString();
	}

	@Override
	public String toString() {
		return stringifyValue();
	}

	public AnnotationEntry getAnnotationEntry() {
		return annotationEntry;
	}
}
