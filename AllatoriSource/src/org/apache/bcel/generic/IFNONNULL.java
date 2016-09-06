/* IFNONNULL - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IFNONNULL extends IfInstruction
{
    private static final long serialVersionUID = -6378085152112796792L;
    
    IFNONNULL() {
	/* empty */
    }
    
    public IFNONNULL(InstructionHandle target) {
	super((short) 199, target);
    }
    
    public IfInstruction negate() {
	return new IFNULL(target);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitBranchInstruction(this);
	v.visitIfInstruction(this);
	v.visitIFNONNULL(this);
    }
}
