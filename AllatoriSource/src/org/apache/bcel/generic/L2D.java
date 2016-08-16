/* L2D - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class L2D extends ConversionInstruction
{
    private static final long serialVersionUID = 1166552346340922659L;
    
    public L2D() {
	super((short) 138);
    }
    
    public void accept(Visitor v) {
	v.visitTypedInstruction(this);
	v.visitStackProducer(this);
	v.visitStackConsumer(this);
	v.visitConversionInstruction(this);
	v.visitL2D(this);
    }
}
