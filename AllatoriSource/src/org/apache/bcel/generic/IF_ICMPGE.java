/* IF_ICMPGE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IF_ICMPGE extends IfInstruction
{
    private static final long serialVersionUID = -1887574519712008723L;
    
    IF_ICMPGE() {
	/* empty */
    }
    
    public IF_ICMPGE(InstructionHandle target) {
	super((short) 162, target);
    }
    
    public IfInstruction negate() {
	return new IF_ICMPLT(target);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitBranchInstruction(this);
	v.visitIfInstruction(this);
	v.visitIF_ICMPGE(this);
    }
}
