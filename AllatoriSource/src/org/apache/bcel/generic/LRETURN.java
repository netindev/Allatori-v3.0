/* LRETURN - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LRETURN extends ReturnInstruction
{
    private static final long serialVersionUID = 6173148526583167918L;
    
    public LRETURN() {
	super((short) 173);
    }
    
    public void accept(Visitor v) {
	v.visitExceptionThrower(this);
	v.visitTypedInstruction(this);
	v.visitStackConsumer(this);
	v.visitReturnInstruction(this);
	v.visitLRETURN(this);
    }
}
