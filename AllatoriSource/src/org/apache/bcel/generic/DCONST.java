/* DCONST - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DCONST extends Instruction implements ConstantPushInstruction
{
    private static final long serialVersionUID = 4421839896759165218L;
    private double value;
    
    DCONST() {
	/* empty */
    }
    
    public DCONST(double f) {
	super((short) 14, (short) 1);
	if (f == 0.0)
	    opcode = (short) 14;
	else if (f == 1.0)
	    opcode = (short) 15;
	else
	    throw new ClassGenException
		      (new StringBuilder().append
			   ("DCONST can be used only for 0.0 and 1.0: ").append
			   (f).toString());
	value = f;
    }
    
    public Number getValue() {
	return new Double(value);
    }
    
    public Type getType(ConstantPoolGen cp) {
	return Type.DOUBLE;
    }
    
    public void accept(Visitor v) {
	v.visitPushInstruction(this);
	v.visitStackProducer(this);
	v.visitTypedInstruction(this);
	v.visitConstantPushInstruction(this);
	v.visitDCONST(this);
    }
}
