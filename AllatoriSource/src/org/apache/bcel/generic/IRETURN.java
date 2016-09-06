/* IRETURN - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class IRETURN extends ReturnInstruction
{
    private static final long serialVersionUID = 4067606299818510584L;
    
    public IRETURN() {
	super((short) 172);
    }
    
    public void accept(Visitor v) {
	v.visitExceptionThrower(this);
	v.visitTypedInstruction(this);
	v.visitStackConsumer(this);
	v.visitReturnInstruction(this);
	v.visitIRETURN(this);
    }
}
