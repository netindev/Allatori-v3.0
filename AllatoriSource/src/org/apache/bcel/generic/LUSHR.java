/* LUSHR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class LUSHR extends ArithmeticInstruction
{
    private static final long serialVersionUID = 9184663422046843885L;
    
    public LUSHR() {
	super((short) 125);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitLUSHR(this);
    }
}
