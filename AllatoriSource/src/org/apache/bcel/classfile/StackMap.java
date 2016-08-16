/* StackMap - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class StackMap extends Attribute
{
    private static final long serialVersionUID = -6238662431726968495L;
    private int map_length;
    private StackMapEntry[] map;
    
    public StackMap(int name_index, int length, StackMapEntry[] map,
		    ConstantPool constant_pool) {
	super((byte) 11, name_index, length, constant_pool);
	setStackMap(map);
    }
    
    StackMap(int name_index, int length, DataInputStream file,
	     ConstantPool constant_pool) throws IOException {
	this(name_index, length, (StackMapEntry[]) null, constant_pool);
	map_length = file.readUnsignedShort();
	map = new StackMapEntry[map_length];
	for (int i = 0; i < map_length; i++)
	    map[i] = new StackMapEntry(file, constant_pool);
    }
    
    public final void dump(DataOutputStream file) throws IOException {
	super.dump(file);
	file.writeShort(map_length);
	for (int i = 0; i < map_length; i++)
	    map[i].dump(file);
    }
    
    public final StackMapEntry[] getStackMap() {
	return map;
    }
    
    public final void setStackMap(StackMapEntry[] map) {
	this.map = map;
	map_length = map == null ? 0 : map.length;
    }
    
    public final String toString() {
	StringBuilder buf = new StringBuilder("StackMap(");
	for (int i = 0; i < map_length; i++) {
	    buf.append(map[i].toString());
	    if (i < map_length - 1)
		buf.append(", ");
	}
	buf.append(')');
	return buf.toString();
    }
    
    public Attribute copy(ConstantPool _constant_pool) {
	StackMap c = (StackMap) clone();
	c.map = new StackMapEntry[map_length];
	for (int i = 0; i < map_length; i++)
	    c.map[i] = map[i].copy();
	c.constant_pool = _constant_pool;
	return c;
    }
    
    public void accept(Visitor v) {
	v.visitStackMap(this);
    }
    
    public final int getMapLength() {
	return map_length;
    }
}
