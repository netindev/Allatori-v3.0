/* Annotations - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Annotations extends Attribute
{
    private static final long serialVersionUID = 1L;
    private AnnotationEntry[] annotation_table;
    private final boolean isRuntimeVisible;
    
    public Annotations(byte annotation_type, int name_index, int length,
		       DataInputStream file, ConstantPool constant_pool,
		       boolean isRuntimeVisible) throws IOException {
	this(annotation_type, name_index, length, (AnnotationEntry[]) null,
	     constant_pool, isRuntimeVisible);
	int annotation_table_length = file.readUnsignedShort();
	annotation_table = new AnnotationEntry[annotation_table_length];
	for (int i = 0; i < annotation_table_length; i++)
	    annotation_table[i]
		= AnnotationEntry.read(file, constant_pool, isRuntimeVisible);
    }
    
    public Annotations(byte annotation_type, int name_index, int length,
		       AnnotationEntry[] annotation_table,
		       ConstantPool constant_pool, boolean isRuntimeVisible) {
	super(annotation_type, name_index, length, constant_pool);
	setAnnotationTable(annotation_table);
	this.isRuntimeVisible = isRuntimeVisible;
    }
    
    public void accept(Visitor v) {
	v.visitAnnotation(this);
    }
    
    public final void setAnnotationTable(AnnotationEntry[] annotation_table) {
	this.annotation_table = annotation_table;
    }
    
    public AnnotationEntry[] getAnnotationEntries() {
	return annotation_table;
    }
    
    public final int getNumAnnotations() {
	if (annotation_table == null)
	    return 0;
	return annotation_table.length;
    }
    
    public boolean isRuntimeVisible() {
	return isRuntimeVisible;
    }
    
    protected void writeAnnotations(DataOutputStream dos) throws IOException {
	if (annotation_table != null) {
	    dos.writeShort(annotation_table.length);
	    for (int i = 0; i < annotation_table.length; i++)
		annotation_table[i].dump(dos);
	}
    }
}
