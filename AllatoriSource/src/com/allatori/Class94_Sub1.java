package com.allatori;

import java.util.Arrays;

public class Class94_Sub1 extends Class94 {

    private static String[] aStringArray1335 = new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"};


    public Class94_Sub1() {
        super(aStringArray1335);
    }

    static {
        Arrays.sort(aStringArray1335, new StringComparatorByLength());
    }
}