/* IFNE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IFNE extends IfInstruction
{
    private static final long serialVersionUID = -5417647524534847152L;
    
    IFNE() {
	/* empty */
    }
    
    public IFNE(InstructionHandle target) {
	super((short) 154, target);
    }
    
    public IfInstruction negate() {
	return new IFEQ(target);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitBranchInstruction(this);
	v.visitIfInstruction(this);
	v.visitIFNE(this);
    }
}
