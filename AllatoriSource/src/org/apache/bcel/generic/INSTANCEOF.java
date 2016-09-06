package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class INSTANCEOF extends CPInstruction implements LoadClass, ExceptionThrower, StackProducer, StackConsumer {
	private static final long serialVersionUID = -1068668479062613915L;

	INSTANCEOF() {
	}

	public INSTANCEOF(int index) {
		super((short) 193, index);
	}

	@Override
	public Class<?>[] getExceptions() {
		return ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION;
	}

	@Override
	public ObjectType getLoadClassType(ConstantPoolGen cpg) {
		Type t = getType(cpg);
		if (t instanceof ArrayType)
			t = ((ArrayType) t).getBasicType();
		return t instanceof ObjectType ? (ObjectType) t : null;
	}

	@Override
	public void accept(Visitor v) {
		v.visitLoadClass(this);
		v.visitExceptionThrower(this);
		v.visitStackProducer(this);
		v.visitStackConsumer(this);
		v.visitTypedInstruction(this);
		v.visitCPInstruction(this);
		v.visitINSTANCEOF(this);
	}
}
