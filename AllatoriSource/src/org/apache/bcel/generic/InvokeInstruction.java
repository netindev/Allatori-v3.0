/* InvokeInstruction - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

import java.util.StringTokenizer;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;

public abstract class InvokeInstruction extends FieldOrMethod
		implements ExceptionThrower, StackConsumer, StackProducer {
	private static final long serialVersionUID = 6089031137856650442L;

	InvokeInstruction() {
		/* empty */
	}

	protected InvokeInstruction(short opcode, int index) {
		super(opcode, index);
	}

	@Override
	public String toString(ConstantPool cp) {
		final Constant c = cp.getConstant(index);
		final StringTokenizer tok = new StringTokenizer(cp.constantToString(c));
		return new StringBuilder().append(Constants.OPCODE_NAMES[opcode]).append(" ")
				.append(tok.nextToken().replace('.', '/')).append(tok.nextToken()).toString();
	}

	@Override
	public int consumeStack(ConstantPoolGen cpg) {
		int sum;
		if (opcode == 184)
			sum = 0;
		else
			sum = 1;
		final String signature = getSignature(cpg);
		sum += Type.getArgumentTypesSize(signature);
		return sum;
	}

	@Override
	public int produceStack(ConstantPoolGen cpg) {
		final String signature = getSignature(cpg);
		return Type.getReturnTypeSize(signature);
	}

	@Override
	public Type getType(ConstantPoolGen cpg) {
		return getReturnType(cpg);
	}

	public String getMethodName(ConstantPoolGen cpg) {
		return getName(cpg);
	}

	public Type getReturnType(ConstantPoolGen cpg) {
		return Type.getReturnType(getSignature(cpg));
	}

	public Type[] getArgumentTypes(ConstantPoolGen cpg) {
		return Type.getArgumentTypes(getSignature(cpg));
	}
}
