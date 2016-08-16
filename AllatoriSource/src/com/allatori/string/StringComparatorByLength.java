package com.allatori.string;

import java.util.Comparator;

public class StringComparatorByLength implements Comparator {

    public int compare(Object var1, Object var2) {
        String var3 = (String) var1;
        String var4 = (String) var2;
        return var3.length() - var4.length();
    }
}
