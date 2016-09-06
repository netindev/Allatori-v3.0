/* IF_ICMPNE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IF_ICMPNE extends IfInstruction
{
    private static final long serialVersionUID = -3908465635973274661L;
    
    IF_ICMPNE() {
	/* empty */
    }
    
    public IF_ICMPNE(InstructionHandle target) {
	super((short) 160, target);
    }
    
    public IfInstruction negate() {
	return new IF_ICMPEQ(target);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitBranchInstruction(this);
	v.visitIfInstruction(this);
	v.visitIF_ICMPNE(this);
    }
}
