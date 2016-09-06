/* Visitor - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package org.apache.bcel.classfile;

public interface Visitor
{
    public void visitCode(Code code);
    
    public void visitCodeException(CodeException codeexception);
    
    public void visitConstantClass(ConstantClass constantclass);
    
    public void visitConstantDouble(ConstantDouble constantdouble);
    
    public void visitConstantFieldref(ConstantFieldref constantfieldref);
    
    public void visitConstantFloat(ConstantFloat constantfloat);
    
    public void visitConstantInteger(ConstantInteger constantinteger);
    
    public void visitConstantInterfaceMethodref
	(ConstantInterfaceMethodref constantinterfacemethodref);
    
    public void visitConstantLong(ConstantLong constantlong);
    
    public void visitConstantMethodref(ConstantMethodref constantmethodref);
    
    public void visitConstantNameAndType
	(ConstantNameAndType constantnameandtype);
    
    public void visitConstantPool(ConstantPool constantpool);
    
    public void visitConstantString(ConstantString constantstring);
    
    public void visitConstantUtf8(ConstantUtf8 constantutf8);
    
    public void visitConstantValue(ConstantValue constantvalue);
    
    public void visitDeprecated(Deprecated deprecated);
    
    public void visitExceptionTable(ExceptionTable exceptiontable);
    
    public void visitField(Field field);
    
    public void visitInnerClass(InnerClass innerclass);
    
    public void visitInnerClasses(InnerClasses innerclasses);
    
    public void visitJavaClass(JavaClass javaclass);
    
    public void visitLineNumber(LineNumber linenumber);
    
    public void visitLineNumberTable(LineNumberTable linenumbertable);
    
    public void visitLocalVariable(LocalVariable localvariable);
    
    public void visitLocalVariableTable(LocalVariableTable localvariabletable);
    
    public void visitMethod(Method method);
    
    public void visitSignature(Signature signature);
    
    public void visitSourceFile(SourceFile sourcefile);
    
    public void visitSynthetic(Synthetic synthetic);
    
    public void visitUnknown(Unknown unknown);
    
    public void visitStackMap(StackMap stackmap);
    
    public void visitStackMapEntry(StackMapEntry stackmapentry);
    
    public void visitStackMapTable(StackMapTable stackmaptable);
    
    public void visitStackMapTableEntry(StackMapTableEntry stackmaptableentry);
    
    public void visitAnnotation(Annotations annotations);
    
    public void visitParameterAnnotation
	(ParameterAnnotations parameterannotations);
    
    public void visitAnnotationEntry(AnnotationEntry annotationentry);
    
    public void visitAnnotationDefault(AnnotationDefault annotationdefault);
    
    public void visitLocalVariableTypeTable
	(LocalVariableTypeTable localvariabletypetable);
    
    public void visitEnclosingMethod(EnclosingMethod enclosingmethod);
}
