/* IFEQ - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IFEQ extends IfInstruction
{
    private static final long serialVersionUID = -6140576561545855324L;
    
    IFEQ() {
	/* empty */
    }
    
    public IFEQ(InstructionHandle target) {
	super((short) 153, target);
    }
    
    public IfInstruction negate() {
	return new IFNE(target);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitBranchInstruction(this);
	v.visitIfInstruction(this);
	v.visitIFEQ(this);
    }
}
