/* AnnotationDefault - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AnnotationDefault extends Attribute
{
    private static final long serialVersionUID = -4017327188724019487L;
    ElementValue default_value;
    
    public AnnotationDefault(int name_index, int length, DataInputStream file,
			     ConstantPool constant_pool) throws IOException {
	this(name_index, length, (ElementValue) null, constant_pool);
	default_value = ElementValue.readElementValue(file, constant_pool);
    }
    
    public AnnotationDefault(int name_index, int length,
			     ElementValue defaultValue,
			     ConstantPool constant_pool) {
	super((byte) 16, name_index, length, constant_pool);
	setDefaultValue(defaultValue);
    }
    
    public void accept(Visitor v) {
	/* empty */
    }
    
    public final void setDefaultValue(ElementValue defaultValue) {
	default_value = defaultValue;
    }
    
    public final ElementValue getDefaultValue() {
	return default_value;
    }
    
    public Attribute copy(ConstantPool _constant_pool) {
	throw new RuntimeException("Not implemented yet!");
    }
    
    public final void dump(DataOutputStream dos) throws IOException {
	super.dump(dos);
	default_value.dump(dos);
    }
}
