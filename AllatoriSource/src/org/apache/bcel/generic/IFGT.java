/* IFGT - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IFGT extends IfInstruction
{
    private static final long serialVersionUID = -6271055211127179697L;
    
    IFGT() {
	/* empty */
    }
    
    public IFGT(InstructionHandle target) {
	super((short) 157, target);
    }
    
    public IfInstruction negate() {
	return new IFLE(target);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitBranchInstruction(this);
	v.visitIfInstruction(this);
	v.visitIFGT(this);
    }
}
