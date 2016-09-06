/* ReturnaddressType - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class ReturnaddressType extends Type {
	private static final long serialVersionUID = 3782621476731023927L;
	public static final ReturnaddressType NO_TARGET = new ReturnaddressType();
	private InstructionHandle returnTarget;

	private ReturnaddressType() {
		super((byte) 16, "<return address>");
	}

	public ReturnaddressType(InstructionHandle returnTarget) {
		super((byte) 16,
				new StringBuilder().append("<return address targeting ").append(returnTarget).append(">").toString());
		this.returnTarget = returnTarget;
	}

	@Override
	public int hashCode() {
		if (returnTarget == null)
			return 0;
		return returnTarget.hashCode();
	}

	@Override
	public boolean equals(Object rat) {
		if (!(rat instanceof ReturnaddressType))
			return false;
		final ReturnaddressType that = (ReturnaddressType) rat;
		if (returnTarget == null || that.returnTarget == null)
			return that.returnTarget == returnTarget;
		return that.returnTarget.equals(returnTarget);
	}

	public InstructionHandle getTarget() {
		return returnTarget;
	}
}
