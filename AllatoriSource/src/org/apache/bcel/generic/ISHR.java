/* ISHR - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class ISHR extends ArithmeticInstruction
{
    private static final long serialVersionUID = 7401114050910307281L;
    
    public ISHR() {
	super((short) 122);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitArithmeticInstruction(this);
	v.visitISHR(this);
    }
}
