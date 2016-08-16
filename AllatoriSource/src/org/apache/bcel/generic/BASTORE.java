/* BASTORE - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class BASTORE extends ArrayInstruction implements StackConsumer
{
    private static final long serialVersionUID = 1127770065463906050L;
    
    public BASTORE() {
	super((short) 84);
    }
    
    public void accept(Visitor v) {
	v.visitStackConsumer(this);
	v.visitExceptionThrower(this);
	v.visitTypedInstruction(this);
	v.visitArrayInstruction(this);
	v.visitBASTORE(this);
    }
}
