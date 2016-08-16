/* Class2HTML - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

public class Class2HTML implements Constants
{
    private JavaClass java_class;
    private String dir;
    private static String class_package;
    private static String class_name;
    private static ConstantPool constant_pool;
    
    public Class2HTML(JavaClass java_class, String dir) throws IOException {
	Method[] methods = java_class.getMethods();
	this.java_class = java_class;
	this.dir = dir;
	class_name = java_class.getClassName();
	constant_pool = java_class.getConstantPool();
	int index = class_name.lastIndexOf('.');
	if (index > -1)
	    class_package = class_name.substring(0, index);
	else
	    class_package = "";
	ConstantHTML constant_html
	    = new ConstantHTML(dir, class_name, class_package, methods,
			       constant_pool);
	AttributeHTML attribute_html
	    = new AttributeHTML(dir, class_name, constant_pool, constant_html);
	writeMainHTML(attribute_html);
	new CodeHTML(dir, class_name, methods, constant_pool, constant_html);
	attribute_html.close();
    }
    
    public static void main(String[] argv) {
	String[] file_name = new String[argv.length];
	int files = 0;
	ClassParser parser = null;
	JavaClass java_class = null;
	String zip_file = null;
	char sep = System.getProperty("file.separator").toCharArray()[0];
	String dir = new StringBuilder().append(".").append(sep).toString();
	try {
	    for (int i = 0; i < argv.length; i++) {
		if (argv[i].charAt(0) == '-') {
		    if (argv[i].equals("-d")) {
			dir = argv[++i];
			if (!dir.endsWith(new StringBuilder().append("").append
					      (sep).toString()))
			    dir = new StringBuilder().append(dir).append
				      (sep).toString();
			new File(dir).mkdirs();
		    } else if (argv[i].equals("-zip"))
			zip_file = argv[++i];
		    else
			System.out.println(new StringBuilder().append
					       ("Unknown option ").append
					       (argv[i]).toString());
		} else
		    file_name[files++] = argv[i];
	    }
	    if (files == 0)
		System.err.println("Class2HTML: No input files specified.");
	    else {
		for (int i = 0; i < files; i++) {
		    System.out.print(new StringBuilder().append
					 ("Processing ").append
					 (file_name[i]).append
					 ("...").toString());
		    if (zip_file == null)
			parser = new ClassParser(file_name[i]);
		    else
			parser = new ClassParser(zip_file, file_name[i]);
		    java_class = parser.parse();
		    new Class2HTML(java_class, dir);
		    System.out.println("Done.");
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	    e.printStackTrace(System.out);
	}
    }
    
    static String referenceClass(int index) {
	String str = constant_pool.getConstantString(index, (byte) 7);
	str = Utility.compactClassName(str);
	str = Utility.compactClassName(str, new StringBuilder().append
						(class_package).append
						(".").toString(), true);
	return new StringBuilder().append("<A HREF=\"").append(class_name)
		   .append
		   ("_cp.html#cp").append
		   (index).append
		   ("\" TARGET=ConstantPool>").append
		   (str).append
		   ("</A>").toString();
    }
    
    static final String referenceType(String type) {
	String short_type = Utility.compactClassName(type);
	short_type
	    = Utility.compactClassName(short_type, new StringBuilder().append
						       (class_package).append
						       (".").toString(), true);
	int index = type.indexOf('[');
	String base_type = type;
	if (index > -1)
	    base_type = type.substring(0, index);
	if (base_type.equals("int") || base_type.equals("short")
	    || base_type.equals("boolean") || base_type.equals("void")
	    || base_type.equals("char") || base_type.equals("byte")
	    || base_type.equals("long") || base_type.equals("double")
	    || base_type.equals("float"))
	    return new StringBuilder().append("<FONT COLOR=\"#00FF00\">")
		       .append
		       (type).append
		       ("</FONT>").toString();
	return new StringBuilder().append("<A HREF=\"").append(base_type)
		   .append
		   (".html\" TARGET=_top>").append
		   (short_type).append
		   ("</A>").toString();
    }
    
    static String toHTML(String str) {
	StringBuilder buf = new StringBuilder();
	try {
	    for (int i = 0; i < str.length(); i++) {
		char ch;
		switch (ch = str.charAt(i)) {
		case '<':
		    buf.append("&lt;");
		    break;
		case '>':
		    buf.append("&gt;");
		    break;
		case '\n':
		    buf.append("\\n");
		    break;
		case '\r':
		    buf.append("\\r");
		    break;
		default:
		    buf.append(ch);
		}
	    }
	} catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
	    /* empty */
	}
	return buf.toString();
    }
    
    private void writeMainHTML(AttributeHTML attribute_html)
	throws IOException {
	PrintWriter file
	    = new PrintWriter(new FileOutputStream(new StringBuilder().append
						       (dir).append
						       (class_name).append
						       (".html").toString()));
	Attribute[] attributes = java_class.getAttributes();
	file.println
	    (new StringBuilder().append
		 ("<HTML>\n<HEAD><TITLE>Documentation for ").append
		 (class_name).append
		 ("</TITLE>").append
		 ("</HEAD>\n").append
		 ("<FRAMESET BORDER=1 cols=\"30%,*\">\n").append
		 ("<FRAMESET BORDER=1 rows=\"80%,*\">\n").append
		 ("<FRAME NAME=\"ConstantPool\" SRC=\"").append
		 (class_name).append
		 ("_cp.html").append
		 ("\"\n MARGINWIDTH=\"0\" ").append
		 ("MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n")
		 .append
		 ("<FRAME NAME=\"Attributes\" SRC=\"").append
		 (class_name).append
		 ("_attributes.html").append
		 ("\"\n MARGINWIDTH=\"0\" ").append
		 ("MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n")
		 .append
		 ("</FRAMESET>\n").append
		 ("<FRAMESET BORDER=1 rows=\"80%,*\">\n").append
		 ("<FRAME NAME=\"Code\" SRC=\"").append
		 (class_name).append
		 ("_code.html\"\n MARGINWIDTH=0 ").append
		 ("MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n").append
		 ("<FRAME NAME=\"Methods\" SRC=\"").append
		 (class_name).append
		 ("_methods.html\"\n MARGINWIDTH=0 ").append
		 ("MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n").append
		 ("</FRAMESET></FRAMESET></HTML>").toString());
	file.close();
	for (int i = 0; i < attributes.length; i++)
	    attribute_html.writeAttribute(attributes[i],
					  new StringBuilder().append
					      ("class").append
					      (i).toString());
    }
}
