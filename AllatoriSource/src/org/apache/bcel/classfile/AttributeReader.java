/* AttributeReader - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

import java.io.DataInputStream;

public interface AttributeReader {
	public Attribute createAttribute(int i, int i_0_, DataInputStream datainputstream, ConstantPool constantpool);
}
