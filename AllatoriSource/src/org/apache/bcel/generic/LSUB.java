/* LSUB - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LSUB extends ArithmeticInstruction
{
    private static final long serialVersionUID = -8379864362938353932L;
    
    public LSUB() {
	super((short) 101);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitLSUB(this);
    }
}
