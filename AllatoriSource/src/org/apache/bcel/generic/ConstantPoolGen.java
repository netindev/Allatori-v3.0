/* ConstantPoolGen - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantCP;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;

public class ConstantPoolGen implements Serializable
{
    private static final long serialVersionUID = 6664071417323174824L;
    protected int size;
    protected Constant[] constants;
    protected int index = 1;
    private static final String METHODREF_DELIM = ":";
    private static final String IMETHODREF_DELIM = "#";
    private static final String FIELDREF_DELIM = "&";
    private static final String NAT_DELIM = "%";
    private Map string_table;
    private Map class_table;
    private Map utf8_table;
    private Map n_a_t_table;
    private Map cp_table;
    
    private static class Index implements Serializable
    {
	private static final long serialVersionUID = -9187078620578535161L;
	int index;
	
	Index(int i) {
	    index = i;
	}
    }
    
    public ConstantPoolGen(Constant[] cs) {
	string_table = new HashMap();
	class_table = new HashMap();
	utf8_table = new HashMap();
	n_a_t_table = new HashMap();
	cp_table = new HashMap();
	StringBuilder sb = new StringBuilder(256);
	size = Math.max(256, cs.length + 64);
	constants = new Constant[size];
	System.arraycopy(cs, 0, constants, 0, cs.length);
	if (cs.length > 0)
	    index = cs.length;
	for (int i = 1; i < index; i++) {
	    Constant c = constants[i];
	    if (c instanceof ConstantString) {
		ConstantString s = (ConstantString) c;
		ConstantUtf8 u8 = (ConstantUtf8) constants[s.getStringIndex()];
		String key = u8.getBytes();
		if (!string_table.containsKey(key))
		    string_table.put(key, new Index(i));
	    } else if (c instanceof ConstantClass) {
		ConstantClass s = (ConstantClass) c;
		ConstantUtf8 u8 = (ConstantUtf8) constants[s.getNameIndex()];
		String key = u8.getBytes();
		if (!class_table.containsKey(key))
		    class_table.put(key, new Index(i));
	    } else if (c instanceof ConstantNameAndType) {
		ConstantNameAndType n = (ConstantNameAndType) c;
		ConstantUtf8 u8 = (ConstantUtf8) constants[n.getNameIndex()];
		ConstantUtf8 u8_2
		    = (ConstantUtf8) constants[n.getSignatureIndex()];
		sb.append(u8.getBytes());
		sb.append("%");
		sb.append(u8_2.getBytes());
		String key = sb.toString();
		sb.delete(0, sb.length());
		if (!n_a_t_table.containsKey(key))
		    n_a_t_table.put(key, new Index(i));
	    } else if (c instanceof ConstantUtf8) {
		ConstantUtf8 u = (ConstantUtf8) c;
		String key = u.getBytes();
		if (!utf8_table.containsKey(key))
		    utf8_table.put(key, new Index(i));
	    } else if (c instanceof ConstantCP) {
		ConstantCP m = (ConstantCP) c;
		ConstantClass clazz
		    = (ConstantClass) constants[m.getClassIndex()];
		ConstantNameAndType n
		    = (ConstantNameAndType) constants[m.getNameAndTypeIndex()];
		ConstantUtf8 u8
		    = (ConstantUtf8) constants[clazz.getNameIndex()];
		String class_name = u8.getBytes().replace('/', '.');
		u8 = (ConstantUtf8) constants[n.getNameIndex()];
		String method_name = u8.getBytes();
		u8 = (ConstantUtf8) constants[n.getSignatureIndex()];
		String signature = u8.getBytes();
		String delim = ":";
		if (c instanceof ConstantInterfaceMethodref)
		    delim = "#";
		else if (c instanceof ConstantFieldref)
		    delim = "&";
		sb.append(class_name);
		sb.append(delim);
		sb.append(method_name);
		sb.append(delim);
		sb.append(signature);
		String key = sb.toString();
		sb.delete(0, sb.length());
		if (!cp_table.containsKey(key))
		    cp_table.put(key, new Index(i));
	    }
	}
    }
    
    public ConstantPoolGen(ConstantPool cp) {
	this(cp.getConstantPool());
    }
    
    public ConstantPoolGen() {
	string_table = new HashMap();
	class_table = new HashMap();
	utf8_table = new HashMap();
	n_a_t_table = new HashMap();
	cp_table = new HashMap();
	size = 256;
	constants = new Constant[size];
    }
    
    protected void adjustSize() {
	if (index + 3 >= size) {
	    Constant[] cs = constants;
	    size *= 2;
	    constants = new Constant[size];
	    System.arraycopy(cs, 0, constants, 0, index);
	}
    }
    
    public int lookupString(String str) {
	Index index = (Index) string_table.get(str);
	return index != null ? index.index : -1;
    }
    
    public int addString(String str) {
	int ret;
	if ((ret = lookupString(str)) != -1)
	    return ret;
	int utf8 = addUtf8(str);
	adjustSize();
	ConstantString s = new ConstantString(utf8);
	ret = index;
	constants[index++] = s;
	if (!string_table.containsKey(str))
	    string_table.put(str, new Index(ret));
	return ret;
    }
    
    public int lookupClass(String str) {
	Index index = (Index) class_table.get(str.replace('.', '/'));
	return index != null ? index.index : -1;
    }
    
    private int addClass_(String clazz) {
	int ret;
	if ((ret = lookupClass(clazz)) != -1)
	    return ret;
	adjustSize();
	ConstantClass c = new ConstantClass(addUtf8(clazz));
	ret = index;
	constants[index++] = c;
	if (!class_table.containsKey(clazz))
	    class_table.put(clazz, new Index(ret));
	return ret;
    }
    
    public int addClass(String str) {
	return addClass_(str.replace('.', '/'));
    }
    
    public int addClass(ObjectType type) {
	return addClass(type.getClassName());
    }
    
    public int addArrayClass(ArrayType type) {
	return addClass_(type.getSignature());
    }
    
    public int lookupInteger(int n) {
	for (int i = 1; i < index; i++) {
	    if (constants[i] instanceof ConstantInteger) {
		ConstantInteger c = (ConstantInteger) constants[i];
		if (c.getBytes() == n)
		    return i;
	    }
	}
	return -1;
    }
    
    public int addInteger(int n) {
	int ret;
	if ((ret = lookupInteger(n)) != -1)
	    return ret;
	adjustSize();
	ret = index;
	constants[index++] = new ConstantInteger(n);
	return ret;
    }
    
    public int lookupFloat(float n) {
	int bits = Float.floatToIntBits(n);
	for (int i = 1; i < index; i++) {
	    if (constants[i] instanceof ConstantFloat) {
		ConstantFloat c = (ConstantFloat) constants[i];
		if (Float.floatToIntBits(c.getBytes()) == bits)
		    return i;
	    }
	}
	return -1;
    }
    
    public int addFloat(float n) {
	int ret;
	if ((ret = lookupFloat(n)) != -1)
	    return ret;
	adjustSize();
	ret = index;
	constants[index++] = new ConstantFloat(n);
	return ret;
    }
    
    public int lookupUtf8(String n) {
	Index index = (Index) utf8_table.get(n);
	return index != null ? index.index : -1;
    }
    
    public int addUtf8(String n) {
	int ret;
	if ((ret = lookupUtf8(n)) != -1)
	    return ret;
	adjustSize();
	ret = index;
	constants[index++] = new ConstantUtf8(n);
	if (!utf8_table.containsKey(n))
	    utf8_table.put(n, new Index(ret));
	return ret;
    }
    
    public int lookupLong(long n) {
	for (int i = 1; i < index; i++) {
	    if (constants[i] instanceof ConstantLong) {
		ConstantLong c = (ConstantLong) constants[i];
		if (c.getBytes() == n)
		    return i;
	    }
	}
	return -1;
    }
    
    public int addLong(long n) {
	int ret;
	if ((ret = lookupLong(n)) != -1)
	    return ret;
	adjustSize();
	ret = index;
	constants[index] = new ConstantLong(n);
	index += 2;
	return ret;
    }
    
    public int lookupDouble(double n) {
	long bits = Double.doubleToLongBits(n);
	for (int i = 1; i < index; i++) {
	    if (constants[i] instanceof ConstantDouble) {
		ConstantDouble c = (ConstantDouble) constants[i];
		if (Double.doubleToLongBits(c.getBytes()) == bits)
		    return i;
	    }
	}
	return -1;
    }
    
    public int addDouble(double n) {
	int ret;
	if ((ret = lookupDouble(n)) != -1)
	    return ret;
	adjustSize();
	ret = index;
	constants[index] = new ConstantDouble(n);
	index += 2;
	return ret;
    }
    
    public int lookupNameAndType(String name, String signature) {
	Index _index
	    = (Index) n_a_t_table.get(new StringBuilder().append(name).append
					  ("%").append
					  (signature).toString());
	return _index != null ? _index.index : -1;
    }
    
    public int addNameAndType(String name, String signature) {
	int ret;
	if ((ret = lookupNameAndType(name, signature)) != -1)
	    return ret;
	adjustSize();
	int name_index = addUtf8(name);
	int signature_index = addUtf8(signature);
	ret = index;
	constants[index++]
	    = new ConstantNameAndType(name_index, signature_index);
	String key = new StringBuilder().append(name).append("%").append
			 (signature).toString();
	if (!n_a_t_table.containsKey(key))
	    n_a_t_table.put(key, new Index(ret));
	return ret;
    }
    
    public int lookupMethodref(String class_name, String method_name,
			       String signature) {
	Index index = (Index) cp_table.get(new StringBuilder().append
					       (class_name).append
					       (":").append
					       (method_name).append
					       (":").append
					       (signature).toString());
	return index != null ? index.index : -1;
    }
    
    public int lookupMethodref(MethodGen method) {
	return lookupMethodref(method.getClassName(), method.getName(),
			       method.getSignature());
    }
    
    public int addMethodref(String class_name, String method_name,
			    String signature) {
	int ret;
	if ((ret = lookupMethodref(class_name, method_name, signature)) != -1)
	    return ret;
	adjustSize();
	int name_and_type_index = addNameAndType(method_name, signature);
	int class_index = addClass(class_name);
	ret = index;
	constants[index++]
	    = new ConstantMethodref(class_index, name_and_type_index);
	String key = new StringBuilder().append(class_name).append(":").append
			 (method_name).append
			 (":").append
			 (signature).toString();
	if (!cp_table.containsKey(key))
	    cp_table.put(key, new Index(ret));
	return ret;
    }
    
    public int addMethodref(MethodGen method) {
	return addMethodref(method.getClassName(), method.getName(),
			    method.getSignature());
    }
    
    public int lookupInterfaceMethodref(String class_name, String method_name,
					String signature) {
	Index index = (Index) cp_table.get(new StringBuilder().append
					       (class_name).append
					       ("#").append
					       (method_name).append
					       ("#").append
					       (signature).toString());
	return index != null ? index.index : -1;
    }
    
    public int lookupInterfaceMethodref(MethodGen method) {
	return lookupInterfaceMethodref(method.getClassName(),
					method.getName(),
					method.getSignature());
    }
    
    public int addInterfaceMethodref(String class_name, String method_name,
				     String signature) {
	int ret;
	if ((ret
	     = lookupInterfaceMethodref(class_name, method_name, signature))
	    != -1)
	    return ret;
	adjustSize();
	int class_index = addClass(class_name);
	int name_and_type_index = addNameAndType(method_name, signature);
	ret = index;
	constants[index++]
	    = new ConstantInterfaceMethodref(class_index, name_and_type_index);
	String key = new StringBuilder().append(class_name).append("#").append
			 (method_name).append
			 ("#").append
			 (signature).toString();
	if (!cp_table.containsKey(key))
	    cp_table.put(key, new Index(ret));
	return ret;
    }
    
    public int addInterfaceMethodref(MethodGen method) {
	return addInterfaceMethodref(method.getClassName(), method.getName(),
				     method.getSignature());
    }
    
    public int lookupFieldref(String class_name, String field_name,
			      String signature) {
	Index index = (Index) cp_table.get(new StringBuilder().append
					       (class_name).append
					       ("&").append
					       (field_name).append
					       ("&").append
					       (signature).toString());
	return index != null ? index.index : -1;
    }
    
    public int addFieldref(String class_name, String field_name,
			   String signature) {
	int ret;
	if ((ret = lookupFieldref(class_name, field_name, signature)) != -1)
	    return ret;
	adjustSize();
	int class_index = addClass(class_name);
	int name_and_type_index = addNameAndType(field_name, signature);
	ret = index;
	constants[index++]
	    = new ConstantFieldref(class_index, name_and_type_index);
	String key = new StringBuilder().append(class_name).append("&").append
			 (field_name).append
			 ("&").append
			 (signature).toString();
	if (!cp_table.containsKey(key))
	    cp_table.put(key, new Index(ret));
	return ret;
    }
    
    public Constant getConstant(int i) {
	return constants[i];
    }
    
    public void setConstant(int i, Constant c) {
	constants[i] = c;
    }
    
    public ConstantPool getConstantPool() {
	return new ConstantPool(constants);
    }
    
    public int getSize() {
	return index;
    }
    
    public ConstantPool getFinalConstantPool() {
	Constant[] cs = new Constant[index];
	System.arraycopy(constants, 0, cs, 0, index);
	return new ConstantPool(cs);
    }
    
    public String toString() {
	StringBuilder buf = new StringBuilder();
	for (int i = 1; i < index; i++)
	    buf.append(i).append(")").append(constants[i]).append("\n");
	return buf.toString();
    }
    
    public int addConstant(Constant c, ConstantPoolGen cp) {
	Constant[] constants = cp.getConstantPool().getConstantPool();
	switch (c.getTag()) {
	case 8: {
	    ConstantString s = (ConstantString) c;
	    ConstantUtf8 u8 = (ConstantUtf8) constants[s.getStringIndex()];
	    return addString(u8.getBytes());
	}
	case 7: {
	    ConstantClass s = (ConstantClass) c;
	    ConstantUtf8 u8 = (ConstantUtf8) constants[s.getNameIndex()];
	    return addClass(u8.getBytes());
	}
	case 12: {
	    ConstantNameAndType n = (ConstantNameAndType) c;
	    ConstantUtf8 u8 = (ConstantUtf8) constants[n.getNameIndex()];
	    ConstantUtf8 u8_2
		= (ConstantUtf8) constants[n.getSignatureIndex()];
	    return addNameAndType(u8.getBytes(), u8_2.getBytes());
	}
	case 1:
	    return addUtf8(((ConstantUtf8) c).getBytes());
	case 6:
	    return addDouble(((ConstantDouble) c).getBytes());
	case 4:
	    return addFloat(((ConstantFloat) c).getBytes());
	case 5:
	    return addLong(((ConstantLong) c).getBytes());
	case 3:
	    return addInteger(((ConstantInteger) c).getBytes());
	case 9:
	case 10:
	case 11: {
	    ConstantCP m = (ConstantCP) c;
	    ConstantClass clazz = (ConstantClass) constants[m.getClassIndex()];
	    ConstantNameAndType n
		= (ConstantNameAndType) constants[m.getNameAndTypeIndex()];
	    ConstantUtf8 u8 = (ConstantUtf8) constants[clazz.getNameIndex()];
	    String class_name = u8.getBytes().replace('/', '.');
	    u8 = (ConstantUtf8) constants[n.getNameIndex()];
	    String name = u8.getBytes();
	    u8 = (ConstantUtf8) constants[n.getSignatureIndex()];
	    String signature = u8.getBytes();
	    switch (c.getTag()) {
	    case 11:
		return addInterfaceMethodref(class_name, name, signature);
	    case 10:
		return addMethodref(class_name, name, signature);
	    case 9:
		return addFieldref(class_name, name, signature);
	    default:
		throw new RuntimeException(new StringBuilder().append
					       ("Unknown constant type ")
					       .append
					       (c).toString());
	    }
	}
	default:
	    throw new RuntimeException(new StringBuilder().append
					   ("Unknown constant type ").append
					   (c).toString());
	}
    }
}
