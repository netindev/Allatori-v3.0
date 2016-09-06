/* ILOAD - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class ILOAD extends LoadInstruction
{
    private static final long serialVersionUID = 7568563453093184347L;
    
    ILOAD() {
	super((short) 21, (short) 26);
    }
    
    public ILOAD(int n) {
	super((short) 21, (short) 26, n);
    }
    
    public void accept(Visitor v) {
	super.accept(v);
	v.visitILOAD(this);
    }
}
