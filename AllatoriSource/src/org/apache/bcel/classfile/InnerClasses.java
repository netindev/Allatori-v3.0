/* InnerClasses - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class InnerClasses extends Attribute
{
    private static final long serialVersionUID = 4570147726361753700L;
    private InnerClass[] inner_classes;
    private int number_of_classes;
    
    public InnerClasses(InnerClasses c) {
	this(c.getNameIndex(), c.getLength(), c.getInnerClasses(),
	     c.getConstantPool());
    }
    
    public InnerClasses(int name_index, int length, InnerClass[] inner_classes,
			ConstantPool constant_pool) {
	super((byte) 6, name_index, length, constant_pool);
	setInnerClasses(inner_classes);
    }
    
    InnerClasses(int name_index, int length, DataInputStream file,
		 ConstantPool constant_pool) throws IOException {
	this(name_index, length, (InnerClass[]) null, constant_pool);
	number_of_classes = file.readUnsignedShort();
	inner_classes = new InnerClass[number_of_classes];
	for (int i = 0; i < number_of_classes; i++)
	    inner_classes[i] = new InnerClass(file);
    }
    
    public void accept(Visitor v) {
	v.visitInnerClasses(this);
    }
    
    public final void dump(DataOutputStream file) throws IOException {
	super.dump(file);
	file.writeShort(number_of_classes);
	for (int i = 0; i < number_of_classes; i++)
	    inner_classes[i].dump(file);
    }
    
    public final InnerClass[] getInnerClasses() {
	return inner_classes;
    }
    
    public final void setInnerClasses(InnerClass[] inner_classes) {
	this.inner_classes = inner_classes;
	number_of_classes = inner_classes == null ? 0 : inner_classes.length;
    }
    
    public final String toString() {
	StringBuilder buf = new StringBuilder();
	for (int i = 0; i < number_of_classes; i++)
	    buf.append(inner_classes[i].toString(constant_pool)).append("\n");
	return buf.toString();
    }
    
    public Attribute copy(ConstantPool _constant_pool) {
	InnerClasses c = (InnerClasses) clone();
	c.inner_classes = new InnerClass[number_of_classes];
	for (int i = 0; i < number_of_classes; i++)
	    c.inner_classes[i] = inner_classes[i].copy();
	c.constant_pool = _constant_pool;
	return c;
    }
}
