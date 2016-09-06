/* ParameterAnnotationEntry - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.Constants;

public class ParameterAnnotationEntry implements Node, Constants
{
    private int annotation_table_length;
    private AnnotationEntry[] annotation_table;
    
    ParameterAnnotationEntry
	(DataInputStream file, ConstantPool constant_pool) throws IOException {
	annotation_table_length = file.readUnsignedShort();
	annotation_table = new AnnotationEntry[annotation_table_length];
	for (int i = 0; i < annotation_table_length; i++)
	    annotation_table[i]
		= AnnotationEntry.read(file, constant_pool, false);
    }
    
    public void accept(Visitor v) {
	/* empty */
    }
    
    public final int getNumAnnotations() {
	return annotation_table_length;
    }
    
    public AnnotationEntry[] getAnnotationEntries() {
	return annotation_table;
    }
    
    public void dump(DataOutputStream dos) throws IOException {
	dos.writeShort(annotation_table_length);
	for (int i = 0; i < annotation_table_length; i++)
	    annotation_table[i].dump(dos);
    }
}
