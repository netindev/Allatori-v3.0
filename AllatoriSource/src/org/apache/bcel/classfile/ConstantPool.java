/* ConstantPool - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.bcel.Constants;

public class ConstantPool implements Cloneable, Node, Serializable
{
    private static final long serialVersionUID = -9093478476423540196L;
    private int constant_pool_count;
    private Constant[] constant_pool;
    
    public ConstantPool(Constant[] constant_pool) {
	setConstantPool(constant_pool);
    }
    
    ConstantPool(DataInputStream file)
	throws IOException, ClassFormatException {
	constant_pool_count = file.readUnsignedShort();
	constant_pool = new Constant[constant_pool_count];
	for (int i = 1; i < constant_pool_count; i++) {
	    constant_pool[i] = Constant.readConstant(file);
	    byte tag = constant_pool[i].getTag();
	    if (tag == 6 || tag == 5)
		i++;
	}
    }
    
    public void accept(Visitor v) {
	v.visitConstantPool(this);
    }
    
    public String constantToString(Constant c) throws ClassFormatException {
	byte tag = c.getTag();
	String str;
	switch (tag) {
	case 7: {
	    int i = ((ConstantClass) c).getNameIndex();
	    c = getConstant(i, (byte) 1);
	    str = Utility.compactClassName(((ConstantUtf8) c).getBytes(),
					   false);
	    break;
	}
	case 8: {
	    int i = ((ConstantString) c).getStringIndex();
	    c = getConstant(i, (byte) 1);
	    str = new StringBuilder().append("\"").append
		      (escape(((ConstantUtf8) c).getBytes())).append
		      ("\"").toString();
	    break;
	}
	case 1:
	    str = ((ConstantUtf8) c).getBytes();
	    break;
	case 6:
	    str = String.valueOf(((ConstantDouble) c).getBytes());
	    break;
	case 4:
	    str = String.valueOf(((ConstantFloat) c).getBytes());
	    break;
	case 5:
	    str = String.valueOf(((ConstantLong) c).getBytes());
	    break;
	case 3:
	    str = String.valueOf(((ConstantInteger) c).getBytes());
	    break;
	case 12:
	    str = new StringBuilder().append
		      (constantToString(((ConstantNameAndType) c)
					    .getNameIndex(),
					(byte) 1))
		      .append
		      (" ").append
		      (constantToString(((ConstantNameAndType) c)
					    .getSignatureIndex(),
					(byte) 1))
		      .toString();
	    break;
	case 9:
	case 10:
	case 11:
	    str = new StringBuilder().append
		      (constantToString(((ConstantCP) c).getClassIndex(),
					(byte) 7))
		      .append
		      (".").append
		      (constantToString(((ConstantCP) c).getNameAndTypeIndex(),
					(byte) 12))
		      .toString();
	    break;
	default:
	    throw new RuntimeException(new StringBuilder().append
					   ("Unknown constant type ").append
					   (tag).toString());
	}
	return str;
    }
    
    private static final String escape(String str) {
	int len = str.length();
	StringBuilder buf = new StringBuilder(len + 5);
	char[] ch = str.toCharArray();
	for (int i = 0; i < len; i++) {
	    switch (ch[i]) {
	    case '\n':
		buf.append("\\n");
		break;
	    case '\r':
		buf.append("\\r");
		break;
	    case '\t':
		buf.append("\\t");
		break;
	    case '\010':
		buf.append("\\b");
		break;
	    case '\"':
		buf.append("\\\"");
		break;
	    default:
		buf.append(ch[i]);
	    }
	}
	return buf.toString();
    }
    
    public String constantToString(int index, byte tag)
	throws ClassFormatException {
	Constant c = getConstant(index, tag);
	return constantToString(c);
    }
    
    public void dump(DataOutputStream file) throws IOException {
	file.writeShort(constant_pool_count);
	for (int i = 1; i < constant_pool_count; i++) {
	    if (constant_pool[i] != null)
		constant_pool[i].dump(file);
	}
    }
    
    public Constant getConstant(int index) {
	if (index >= constant_pool.length || index < 0)
	    throw new ClassFormatException
		      (new StringBuilder().append
			   ("Invalid constant pool reference: ").append
			   (index).append
			   (". Constant pool size is: ").append
			   (constant_pool.length).toString());
	return constant_pool[index];
    }
    
    public Constant getConstant(int index, byte tag)
	throws ClassFormatException {
	Constant c = getConstant(index);
	if (c == null)
	    throw new ClassFormatException(new StringBuilder().append
					       ("Constant pool at index ")
					       .append
					       (index).append
					       (" is null.").toString());
	if (c.getTag() != tag)
	    throw new ClassFormatException(new StringBuilder().append
					       ("Expected class `").append
					       (Constants.CONSTANT_NAMES[tag])
					       .append
					       ("' at index ").append
					       (index).append
					       (" and got ").append
					       (c).toString());
	return c;
    }
    
    public Constant[] getConstantPool() {
	return constant_pool;
    }
    
    public String getConstantString(int index, byte tag)
	throws ClassFormatException {
	Constant c = getConstant(index, tag);
	int i;
	switch (tag) {
	case 7:
	    i = ((ConstantClass) c).getNameIndex();
	    break;
	case 8:
	    i = ((ConstantString) c).getStringIndex();
	    break;
	default:
	    throw new RuntimeException
		      (new StringBuilder().append
			   ("getConstantString called with illegal tag ")
			   .append
			   (tag).toString());
	}
	c = getConstant(i, (byte) 1);
	return ((ConstantUtf8) c).getBytes();
    }
    
    public int getLength() {
	return constant_pool_count;
    }
    
    public void setConstant(int index, Constant constant) {
	constant_pool[index] = constant;
    }
    
    public void setConstantPool(Constant[] constant_pool) {
	this.constant_pool = constant_pool;
	constant_pool_count = constant_pool == null ? 0 : constant_pool.length;
    }
    
    public String toString() {
	StringBuilder buf = new StringBuilder();
	for (int i = 1; i < constant_pool_count; i++)
	    buf.append(i).append(")").append(constant_pool[i]).append("\n");
	return buf.toString();
    }
    
    public ConstantPool copy() {
	ConstantPool c = null;
	try {
	    c = (ConstantPool) this.clone();
	    c.constant_pool = new Constant[constant_pool_count];
	    for (int i = 1; i < constant_pool_count; i++) {
		if (constant_pool[i] != null)
		    c.constant_pool[i] = constant_pool[i].copy();
	    }
	} catch (CloneNotSupportedException clonenotsupportedexception) {
	    /* empty */
	}
	return c;
    }
}
