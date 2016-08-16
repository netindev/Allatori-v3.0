/* F2I - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class F2I extends ConversionInstruction
{
    private static final long serialVersionUID = 6142843856130131105L;
    
    public F2I() {
	super((short) 139);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitConversionInstruction(this);
	v.visitF2I(this);
    }
}
