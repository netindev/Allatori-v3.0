/* ArrayType - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.generic;

public final class ArrayType extends ReferenceType
{
    private static final long serialVersionUID = 7587687059797903734L;
    private int dimensions;
    private Type basic_type;
    
    public ArrayType(byte type, int dimensions) {
	this(BasicType.getType(type), dimensions);
    }
    
    public ArrayType(String class_name, int dimensions) {
	this(new ObjectType(class_name), dimensions);
    }
    
    public ArrayType(Type type, int dimensions) {
	super((byte) 13, "<dummy>");
	if (dimensions < 1 || dimensions > 255)
	    throw new ClassGenException(new StringBuilder().append
					    ("Invalid number of dimensions: ")
					    .append
					    (dimensions).toString());
	switch (type.getType()) {
	case 13: {
	    ArrayType array = (ArrayType) type;
	    this.dimensions = dimensions + array.dimensions;
	    basic_type = array.basic_type;
	    break;
	}
	case 12:
	    throw new ClassGenException("Invalid type: void[]");
	default:
	    this.dimensions = dimensions;
	    basic_type = type;
	}
	StringBuilder buf = new StringBuilder();
	for (int i = 0; i < this.dimensions; i++)
	    buf.append('[');
	buf.append(basic_type.getSignature());
	signature = buf.toString();
    }
    
    public Type getBasicType() {
	return basic_type;
    }
    
    public Type getElementType() {
	if (dimensions == 1)
	    return basic_type;
	return new ArrayType(basic_type, dimensions - 1);
    }
    
    public int getDimensions() {
	return dimensions;
    }
    
    public int hashCode() {
	return basic_type.hashCode() ^ dimensions;
    }
    
    public boolean equals(Object _type) {
	if (_type instanceof ArrayType) {
	    ArrayType array = (ArrayType) _type;
	    return (array.dimensions == dimensions
		    && array.basic_type.equals(basic_type));
	}
	return false;
    }
}
