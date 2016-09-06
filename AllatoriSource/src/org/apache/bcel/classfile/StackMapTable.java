/* StackMapTable - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class StackMapTable extends Attribute
{
    private static final long serialVersionUID = -5802191977296683162L;
    private int map_length;
    private StackMapTableEntry[] map;
    
    public StackMapTable(int name_index, int length, StackMapTableEntry[] map,
			 ConstantPool constant_pool) {
	super((byte) 19, name_index, length, constant_pool);
	setStackMapTable(map);
    }
    
    StackMapTable(int name_index, int length, DataInputStream file,
		  ConstantPool constant_pool) throws IOException {
	this(name_index, length, (StackMapTableEntry[]) null, constant_pool);
	map_length = file.readUnsignedShort();
	map = new StackMapTableEntry[map_length];
	for (int i = 0; i < map_length; i++)
	    map[i] = new StackMapTableEntry(file, constant_pool);
    }
    
    public final void dump(DataOutputStream file) throws IOException {
	super.dump(file);
	file.writeShort(map_length);
	for (int i = 0; i < map_length; i++)
	    map[i].dump(file);
    }
    
    public final StackMapTableEntry[] getStackMapTable() {
	return map;
    }
    
    public final void setStackMapTable(StackMapTableEntry[] map) {
	this.map = map;
	map_length = map == null ? 0 : map.length;
    }
    
    public final String toString() {
	StringBuilder buf = new StringBuilder("StackMapTable(");
	for (int i = 0; i < map_length; i++) {
	    buf.append(map[i].toString());
	    if (i < map_length - 1)
		buf.append(", ");
	}
	buf.append(')');
	return buf.toString();
    }
    
    public Attribute copy(ConstantPool _constant_pool) {
	StackMapTable c = (StackMapTable) clone();
	c.map = new StackMapTableEntry[map_length];
	for (int i = 0; i < map_length; i++)
	    c.map[i] = map[i].copy();
	c.constant_pool = _constant_pool;
	return c;
    }
    
    public void accept(Visitor v) {
	v.visitStackMapTable(this);
    }
    
    public final int getMapLength() {
	return map_length;
    }
}
