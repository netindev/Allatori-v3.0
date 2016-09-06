/* CHECKCAST - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import org.apache.bcel.ExceptionConstants;

public class CHECKCAST extends CPInstruction implements LoadClass, ExceptionThrower, StackProducer, StackConsumer {
	private static final long serialVersionUID = 1227128733786393518L;

	CHECKCAST() {
		/* empty */
	}

	public CHECKCAST(int index) {
		super((short) 192, index);
	}

	@Override
	public Class[] getExceptions() {
		final Class[] cs = new Class[1 + (ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION).length];
		System.arraycopy((ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION), 0, cs, 0,
				(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION).length);
		cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.CLASS_CAST_EXCEPTION;
		return cs;
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
		v.visitCHECKCAST(this);
	}
}
