// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 8/02/2012 16:29:28
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   JavaClass.java

package org.apache.bcel.classfile;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.bcel.generic.Type;
import org.apache.bcel.util.*;

// Referenced classes of package org.apache.bcel.classfile:
//            AccessFlags, Attribute, Field, Method,
//            SourceFile, Annotations, AnnotationEntry, InnerClasses,
//            Node, ConstantPool, Utility, Visitor,
//            InnerClass

public class JavaClass extends AccessFlags
        implements Cloneable, Node, Comparable
{

    public JavaClass(int class_name_index, int superclass_name_index, String file_name, int major, int minor, int access_flags, ConstantPool constant_pool,
                     int interfaces[], Field fields[], org.apache.bcel.classfile.Method methods[], Attribute attributes[], byte source)
    {
        source_file_name = "<Unknown>";
        this.source = 1;
        isAnonymous = false;
        isNested = false;
        computedNestedTypeStatus = false;
        annotationsOutOfDate = true;
        repository = SyntheticRepository.getInstance();
        if(interfaces == null)
            interfaces = new int[0];
        if(attributes == null)
            attributes = new Attribute[0];
        if(fields == null)
            fields = new Field[0];
        if(methods == null)
            methods = new org.apache.bcel.classfile.Method[0];
        this.class_name_index = class_name_index;
        this.superclass_name_index = superclass_name_index;
        this.file_name = file_name;
        this.major = major;
        this.minor = minor;
        this.access_flags = access_flags;
        this.constant_pool = constant_pool;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;
        annotationsOutOfDate = true;
        this.source = source;
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i] instanceof SourceFile) {
                source_file_name = ((SourceFile) attributes[i]).getSourceFileName();
                break;
            }
        }
        class_name = constant_pool.getConstantString(class_name_index, (byte)7);
        class_name = Utility.compactClassName(class_name, false);
        int index = class_name.lastIndexOf('.');
        if(index < 0)
            package_name = "";
        else
            package_name = class_name.substring(0, index);
        if(superclass_name_index > 0)
        {
            superclass_name = constant_pool.getConstantString(superclass_name_index, (byte)7);
            superclass_name = Utility.compactClassName(superclass_name, false);
        } else
        {
            superclass_name = "java.lang.Object";
        }
        interface_names = new String[interfaces.length];
        for(int i = 0; i < interfaces.length; i++)
        {
            String str = constant_pool.getConstantString(interfaces[i], (byte)7);
            interface_names[i] = Utility.compactClassName(str, false);
        }

    }

    public JavaClass(int class_name_index, int superclass_name_index, String file_name, int major, int minor, int access_flags, ConstantPool constant_pool,
                     int interfaces[], Field fields[], org.apache.bcel.classfile.Method methods[], Attribute attributes[])
    {
        this(class_name_index, superclass_name_index, file_name, major, minor, access_flags, constant_pool, interfaces, fields, methods, attributes, (byte)1);
    }

    public void accept(Visitor v)
    {
        v.visitJavaClass(this);
    }

    static final void Debug(String str)
    {
        if(debug)
            System.out.println(str);
    }

    /**
     * Dump class to a file.
     *
     * @param file Output file
     * @throws IOException
     */
    public void dump( File file ) throws IOException {
        String parent = file.getParent();
        if (parent != null) {
            File dir = new File(parent);
            dir.mkdirs();
        }
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(file));
            dump(dos);
        } finally {
            if (dos != null) {
                dos.close();
            }
        }
    }

    public void dump(String _file_name)
            throws IOException
    {
        dump(new File(_file_name));
    }

    /**
     * @return class in binary format
     */
    public byte[] getBytes() {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(s);
        try {
            dump(ds);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ds.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return s.toByteArray();
    }

    public void dump(OutputStream file)
            throws IOException
    {
        dump(new DataOutputStream(file));
    }

    public void dump(DataOutputStream file)
            throws IOException
    {
        file.writeInt(0xcafebabe);
        file.writeShort(minor);
        file.writeShort(major);
        constant_pool.dump(file);
        file.writeShort(access_flags);
        file.writeShort(class_name_index);
        file.writeShort(superclass_name_index);
        file.writeShort(interfaces.length);
        for(int i = 0; i < interfaces.length; i++)
            file.writeShort(interfaces[i]);

        file.writeShort(fields.length);
        for(int i = 0; i < fields.length; i++)
            fields[i].dump(file);

        file.writeShort(methods.length);
        for(int i = 0; i < methods.length; i++)
            methods[i].dump(file);

        if(attributes != null)
        {
            file.writeShort(attributes.length);
            for(int i = 0; i < attributes.length; i++)
                attributes[i].dump(file);

        } else
        {
            file.writeShort(0);
        }
        file.flush();
    }

    public Attribute[] getAttributes()
    {
        return attributes;
    }

    public AnnotationEntry[] getAnnotationEntries()
    {
        if(annotationsOutOfDate)
        {
            Attribute attrs[] = getAttributes();
            List accumulatedAnnotations = new ArrayList();
            for(int i = 0; i < attrs.length; i++)
            {
                Attribute attribute = attrs[i];
                if(!(attribute instanceof Annotations))
                    continue;
                Annotations runtimeAnnotations = (Annotations)attribute;
                for(int j = 0; j < runtimeAnnotations.getAnnotationEntries().length; j++)
                    accumulatedAnnotations.add(runtimeAnnotations.getAnnotationEntries()[j]);

            }

            annotations = (AnnotationEntry[])accumulatedAnnotations.toArray(new AnnotationEntry[accumulatedAnnotations.size()]);
            annotationsOutOfDate = false;
        }
        return annotations;
    }

    public String getClassName()
    {
        return class_name;
    }

    public String getPackageName()
    {
        return package_name;
    }

    public int getClassNameIndex()
    {
        return class_name_index;
    }

    public ConstantPool getConstantPool()
    {
        return constant_pool;
    }

    public Field[] getFields()
    {
        return fields;
    }

    public String getFileName()
    {
        return file_name;
    }

    public String[] getInterfaceNames()
    {
        return interface_names;
    }

    public int[] getInterfaceIndices()
    {
        return interfaces;
    }

    public int getMajor()
    {
        return major;
    }

    public org.apache.bcel.classfile.Method[] getMethods()
    {
        return methods;
    }

    public org.apache.bcel.classfile.Method getMethod(Method m)
    {
        for(int i = 0; i < methods.length; i++)
        {
            org.apache.bcel.classfile.Method method = methods[i];
            if(m.getName().equals(method.getName()) && m.getModifiers() == method.getModifiers() && Type.getSignature(m).equals(method.getSignature()))
                return method;
        }

        return null;
    }

    public int getMinor()
    {
        return minor;
    }

    public String getSourceFileName()
    {
        return source_file_name;
    }

    public String getSuperclassName()
    {
        return superclass_name;
    }

    public int getSuperclassNameIndex()
    {
        return superclass_name_index;
    }

    public void setAttributes(Attribute attributes[])
    {
        this.attributes = attributes;
    }

    public void setClassName(String class_name)
    {
        this.class_name = class_name;
    }

    public void setClassNameIndex(int class_name_index)
    {
        this.class_name_index = class_name_index;
    }

    public void setConstantPool(ConstantPool constant_pool)
    {
        this.constant_pool = constant_pool;
    }

    public void setFields(Field fields[])
    {
        this.fields = fields;
    }

    public void setFileName(String file_name)
    {
        this.file_name = file_name;
    }

    public void setInterfaceNames(String interface_names[])
    {
        this.interface_names = interface_names;
    }

    public void setInterfaces(int interfaces[])
    {
        this.interfaces = interfaces;
    }

    public void setMajor(int major)
    {
        this.major = major;
    }

    public void setMethods(org.apache.bcel.classfile.Method methods[])
    {
        this.methods = methods;
    }

    public void setMinor(int minor)
    {
        this.minor = minor;
    }

    public void setSourceFileName(String source_file_name)
    {
        this.source_file_name = source_file_name;
    }

    public void setSuperclassName(String superclass_name)
    {
        this.superclass_name = superclass_name;
    }

    public void setSuperclassNameIndex(int superclass_name_index)
    {
        this.superclass_name_index = superclass_name_index;
    }

    public String toString()
    {
        String access = Utility.accessToString(access_flags, true);
        access = access.equals("") ? "" : (new StringBuilder()).append(access).append(" ").toString();
        StringBuilder buf = new StringBuilder(128);
        buf.append(access).append(Utility.classOrInterface(access_flags)).append(" ").append(class_name).append(" extends ").append(Utility.compactClassName(superclass_name, false)).append('\n');
        int size = interfaces.length;
        if(size > 0)
        {
            buf.append("implements\t\t");
            for(int i = 0; i < size; i++)
            {
                buf.append(interface_names[i]);
                if(i < size - 1)
                    buf.append(", ");
            }

            buf.append('\n');
        }
        buf.append("filename\t\t").append(file_name).append('\n');
        buf.append("compiled from\t\t").append(source_file_name).append('\n');
        buf.append("compiler version\t").append(major).append(".").append(minor).append('\n');
        buf.append("access flags\t\t").append(access_flags).append('\n');
        buf.append("constant pool\t\t").append(constant_pool.getLength()).append(" entries\n");
        buf.append("ACC_SUPER flag\t\t").append(isSuper()).append("\n");
        if(attributes.length > 0)
        {
            buf.append("\nAttribute(s):\n");
            for(int i = 0; i < attributes.length; i++)
                buf.append(indent(attributes[i]));

        }
        AnnotationEntry annotations[] = getAnnotationEntries();
        if(annotations != null && annotations.length > 0)
        {
            buf.append("\nAnnotation(s):\n");
            for(int i = 0; i < annotations.length; i++)
                buf.append(indent(annotations[i]));

        }
        if(fields.length > 0)
        {
            buf.append("\n").append(fields.length).append(" fields:\n");
            for(int i = 0; i < fields.length; i++)
                buf.append("\t").append(fields[i]).append('\n');

        }
        if(methods.length > 0)
        {
            buf.append("\n").append(methods.length).append(" methods:\n");
            for(int i = 0; i < methods.length; i++)
                buf.append("\t").append(methods[i]).append('\n');

        }
        return buf.toString();
    }

    private static final String indent(Object obj)
    {
        StringTokenizer tok = new StringTokenizer(obj.toString(), "\n");
        StringBuilder buf = new StringBuilder();
        for(; tok.hasMoreTokens(); buf.append("\t").append(tok.nextToken()).append("\n"));
        return buf.toString();
    }

    public JavaClass copy()
    {
        JavaClass c = null;
        try
        {
            c = (JavaClass)clone();
            c.constant_pool = constant_pool.copy();
            c.interfaces = (int[])interfaces.clone();
            c.interface_names = (String[])interface_names.clone();
            c.fields = new Field[fields.length];
            for(int i = 0; i < fields.length; i++)
                c.fields[i] = fields[i].copy(c.constant_pool);

            c.methods = new org.apache.bcel.classfile.Method[methods.length];
            for(int i = 0; i < methods.length; i++)
                c.methods[i] = methods[i].copy(c.constant_pool);

            c.attributes = new Attribute[attributes.length];
            for(int i = 0; i < attributes.length; i++)
                c.attributes[i] = attributes[i].copy(c.constant_pool);

        }
        catch(CloneNotSupportedException e) { }
        return c;
    }

    public final boolean isSuper()
    {
        return (access_flags & 0x20) != 0;
    }

    public final boolean isClass()
    {
        return (access_flags & 0x200) == 0;
    }

    public final boolean isAnonymous()
    {
        computeNestedTypeStatus();
        return isAnonymous;
    }

    public final boolean isNested()
    {
        computeNestedTypeStatus();
        return isNested;
    }

    private final void computeNestedTypeStatus()
    {
        if(computedNestedTypeStatus)
            return;
        for(int i = 0; i < attributes.length; i++)
        {
            if(!(attributes[i] instanceof InnerClasses))
                continue;
            InnerClass innerClasses[] = ((InnerClasses)attributes[i]).getInnerClasses();
            for(int j = 0; j < innerClasses.length; j++)
            {
                boolean innerClassAttributeRefersToMe = false;
                String inner_class_name = constant_pool.getConstantString(innerClasses[j].getInnerClassIndex(), (byte)7);
                inner_class_name = Utility.compactClassName(inner_class_name);
                if(inner_class_name.equals(getClassName()))
                    innerClassAttributeRefersToMe = true;
                if(!innerClassAttributeRefersToMe)
                    continue;
                isNested = true;
                if(innerClasses[j].getInnerNameIndex() == 0)
                    isAnonymous = true;
            }

        }

        computedNestedTypeStatus = true;
    }

    public final byte getSource()
    {
        return source;
    }

    public Repository getRepository()
    {
        return repository;
    }

    public void setRepository(Repository repository)
    {
        this.repository = repository;
    }

    public final boolean instanceOf(JavaClass super_class)
            throws ClassNotFoundException
    {
        if(equals(super_class))
            return true;
        JavaClass super_classes[] = getSuperClasses();
        for(int i = 0; i < super_classes.length; i++)
            if(super_classes[i].equals(super_class))
                return true;

        if(super_class.isInterface())
            return implementationOf(super_class);
        else
            return false;
    }

    public boolean implementationOf(JavaClass inter)
            throws ClassNotFoundException
    {
        if(!inter.isInterface())
            throw new IllegalArgumentException((new StringBuilder()).append(inter.getClassName()).append(" is no interface").toString());
        if(equals(inter))
            return true;
        JavaClass super_interfaces[] = getAllInterfaces();
        for(int i = 0; i < super_interfaces.length; i++)
            if(super_interfaces[i].equals(inter))
                return true;

        return false;
    }

    public JavaClass getSuperClass()
            throws ClassNotFoundException
    {
        if("java.lang.Object".equals(getClassName()))
            return null;
        else
            return repository.loadClass(getSuperclassName());
    }

    public JavaClass[] getSuperClasses()
            throws ClassNotFoundException
    {
        JavaClass clazz = this;
        List allSuperClasses = new ArrayList();
        for(clazz = clazz.getSuperClass(); clazz != null; clazz = clazz.getSuperClass())
            allSuperClasses.add(clazz);

        return (JavaClass[])allSuperClasses.toArray(new JavaClass[allSuperClasses.size()]);
    }

    public JavaClass[] getInterfaces()
            throws ClassNotFoundException
    {
        String _interfaces[] = getInterfaceNames();
        JavaClass classes[] = new JavaClass[_interfaces.length];
        for(int i = 0; i < _interfaces.length; i++)
            classes[i] = repository.loadClass(_interfaces[i]);

        return classes;
    }

    public JavaClass[] getAllInterfaces()
            throws ClassNotFoundException
    {
        ClassQueue queue = new ClassQueue();
        Set allInterfaces = new TreeSet();
        queue.enqueue(this);
        while(!queue.empty())
        {
            JavaClass clazz = queue.dequeue();
            JavaClass souper = clazz.getSuperClass();
            JavaClass _interfaces[] = clazz.getInterfaces();
            if(clazz.isInterface())
                allInterfaces.add(clazz);
            else
            if(souper != null)
                queue.enqueue(souper);
            int i = 0;
            while(i < _interfaces.length)
            {
                queue.enqueue(_interfaces[i]);
                i++;
            }
        }
        return (JavaClass[])allInterfaces.toArray(new JavaClass[allInterfaces.size()]);
    }

    public static BCELComparator getComparator()
    {
        return _cmp;
    }

    public static void setComparator(BCELComparator comparator)
    {
        _cmp = comparator;
    }

    public boolean equals(Object obj)
    {
        return _cmp.equals(this, obj);
    }

    public int compareTo(JavaClass obj)
    {
        return getClassName().compareTo(obj.getClassName());
    }

    public int hashCode()
    {
        return _cmp.hashCode(this);
    }

    public int compareTo(Object x0)
    {
        return compareTo((JavaClass)x0);
    }

    private static final long serialVersionUID = 0x1e3e7b46c406282bL;
    private String file_name;
    private String package_name;
    private String source_file_name;
    private int class_name_index;
    private int superclass_name_index;
    private String class_name;
    private String superclass_name;
    private int major;
    private int minor;
    private ConstantPool constant_pool;
    private int interfaces[];
    private String interface_names[];
    private Field fields[];
    private org.apache.bcel.classfile.Method methods[];
    private Attribute attributes[];
    private AnnotationEntry annotations[];
    private byte source;
    private boolean isAnonymous;
    private boolean isNested;
    private boolean computedNestedTypeStatus;
    public static final byte HEAP = 1;
    public static final byte FILE = 2;
    public static final byte ZIP = 3;
    static boolean debug = false;
    static char sep = '/';
    private boolean annotationsOutOfDate;
    private static BCELComparator _cmp = new BCELComparator() {

        public boolean equals( Object o1, Object o2 ) {
            JavaClass THIS = (JavaClass) o1;
            JavaClass THAT = (JavaClass) o2;
            return THIS.getClassName().equals(THAT.getClassName());
        }


        public int hashCode( Object o ) {
            JavaClass THIS = (JavaClass) o;
            return THIS.getClassName().hashCode();
        }
    };
    private transient Repository repository;

    static
    {
        debug = Boolean.getBoolean("JavaClass.debug");
        String _sep = System.getProperty("file.separator");
        if(_sep != null)
            try
            {
                sep = _sep.charAt(0);
            }
            catch(StringIndexOutOfBoundsException e) { }
    }
}
