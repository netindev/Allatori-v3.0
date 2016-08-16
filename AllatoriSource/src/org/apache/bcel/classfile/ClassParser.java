/* ClassParser - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ClassParser
{
    private DataInputStream file;
    private boolean fileOwned;
    private String file_name;
    private String zip_file;
    private int class_name_index;
    private int superclass_name_index;
    private int major;
    private int minor;
    private int access_flags;
    private int[] interfaces;
    private ConstantPool constant_pool;
    private Field[] fields;
    private Method[] methods;
    private Attribute[] attributes;
    private boolean is_zip;
    private static final int BUFSIZE = 8192;
    
    public ClassParser(InputStream file, String file_name) {
	this.file_name = file_name;
	fileOwned = false;
	String clazz = file.getClass().getName();
	is_zip = (clazz.startsWith("java.util.zip.")
		  || clazz.startsWith("java.util.jar."));
	if (file instanceof DataInputStream)
	    this.file = (DataInputStream) file;
	else
	    this.file
		= new DataInputStream(new BufferedInputStream(file, 8192));
    }
    
    public ClassParser(String file_name) {
	is_zip = false;
	this.file_name = file_name;
	fileOwned = true;
    }
    
    public ClassParser(String zip_file, String file_name) {
	is_zip = true;
	fileOwned = true;
	this.zip_file = zip_file;
	this.file_name = file_name;
    }

    public JavaClass parse() throws IOException, ClassFormatException {
        ZipFile zip = null;
        try {
            if (fileOwned) {
                if (is_zip) {
                    zip = new ZipFile(zip_file);
                    ZipEntry entry = zip.getEntry(file_name);
                    file = new DataInputStream(new BufferedInputStream(zip.getInputStream(entry),
                            BUFSIZE));
                } else {
                    file = new DataInputStream(new BufferedInputStream(new FileInputStream(
                            file_name), BUFSIZE));
                }
            }
            /****************** Read headers ********************************/
            // Check magic tag of class file
            readID();
            // Get compiler version
            readVersion();
            /****************** Read constant pool and related **************/
            // Read constant pool entries
            readConstantPool();
            // Get class information
            readClassInfo();
            // Get interface information, i.e., implemented interfaces
            readInterfaces();
            /****************** Read class fields and methods ***************/
            // Read class fields, i.e., the variables of the class
            readFields();
            // Read class methods, i.e., the functions in the class
            readMethods();
            // Read class attributes
            readAttributes();
            // Check for unknown variables
            //Unknown[] u = Unknown.getUnknownAttributes();
            //for(int i=0; i < u.length; i++)
            //  System.err.println("WARNING: " + u[i]);
            // Everything should have been read now
            //      if(file.available() > 0) {
            //        int bytes = file.available();
            //        byte[] buf = new byte[bytes];
            //        file.read(buf);
            //        if(!(is_zip && (buf.length == 1))) {
            //  	System.err.println("WARNING: Trailing garbage at end of " + file_name);
            //  	System.err.println(bytes + " extra bytes: " + Utility.toHexString(buf));
            //        }
            //      }
        } finally {
            // Read everything of interest, so close the file
            if (fileOwned) {
                file.close();
                if (zip != null) {
                    zip.close();
                }
            }
        }
        // Return the information we have gathered in a new object
        return new JavaClass(class_name_index, superclass_name_index, file_name, major, minor,
                access_flags, constant_pool, interfaces, fields, methods, attributes, is_zip
                ? JavaClass.ZIP
                : JavaClass.FILE);
    }
    
    private final void readAttributes()
	throws IOException, ClassFormatException {
	int attributes_count = file.readUnsignedShort();
	attributes = new Attribute[attributes_count];
	for (int i = 0; i < attributes_count; i++)
	    attributes[i] = Attribute.readAttribute(file, constant_pool);
    }
    
    private final void readClassInfo()
	throws IOException, ClassFormatException {
	access_flags = file.readUnsignedShort();
	if ((access_flags & 0x200) != 0)
	    access_flags |= 0x400;
	if ((access_flags & 0x400) != 0 && (access_flags & 0x10) != 0)
	    throw new ClassFormatException
		      (new StringBuilder().append("Class ").append
			   (file_name).append
			   (" can't be both final and abstract").toString());
	class_name_index = file.readUnsignedShort();
	superclass_name_index = file.readUnsignedShort();
    }
    
    private final void readConstantPool()
	throws IOException, ClassFormatException {
	constant_pool = new ConstantPool(file);
    }
    
    private final void readFields() throws IOException, ClassFormatException {
	int fields_count = file.readUnsignedShort();
	fields = new Field[fields_count];
	for (int i = 0; i < fields_count; i++)
	    fields[i] = new Field(file, constant_pool);
    }
    
    private final void readID() throws IOException, ClassFormatException {
	int magic = -889275714;
	if (file.readInt() != magic)
	    throw new ClassFormatException(new StringBuilder().append
					       (file_name).append
					       (" is not a Java .class file")
					       .toString());
    }
    
    private final void readInterfaces()
	throws IOException, ClassFormatException {
	int interfaces_count = file.readUnsignedShort();
	interfaces = new int[interfaces_count];
	for (int i = 0; i < interfaces_count; i++)
	    interfaces[i] = file.readUnsignedShort();
    }
    
    private final void readMethods() throws IOException, ClassFormatException {
	int methods_count = file.readUnsignedShort();
	methods = new Method[methods_count];
	for (int i = 0; i < methods_count; i++)
	    methods[i] = new Method(file, constant_pool);
    }
    
    private final void readVersion() throws IOException, ClassFormatException {
	minor = file.readUnsignedShort();
	major = file.readUnsignedShort();
    }
}
