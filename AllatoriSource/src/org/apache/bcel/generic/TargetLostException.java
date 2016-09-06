/* TargetLostException - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public final class TargetLostException extends Exception
{
    private static final long serialVersionUID = -6857272667645328384L;
    private InstructionHandle[] targets;
    
    TargetLostException(InstructionHandle[] t, String mesg) {
	super(mesg);
	targets = t;
    }
    
    public InstructionHandle[] getTargets() {
	return targets;
    }
}
