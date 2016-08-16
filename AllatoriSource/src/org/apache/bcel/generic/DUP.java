/* DUP - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public class DUP extends StackInstruction implements PushInstruction
{
    private static final long serialVersionUID = 2297553463589366154L;
    
    public DUP() {
	super((short) 89);
    }
    
    public void accept(Visitor v) {
	v.visitStackProducer(this);
	v.visitPushInstruction(this);
	v.visitStackInstruction(this);
	v.visitDUP(this);
    }
}
