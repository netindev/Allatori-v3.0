package com.allatori;

import org.apache.bcel.generic.ClassGen;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class DefaultHandler_Sub2 extends DefaultHandler {

    private String newClassName;
    private String oldClassName;
    private RenamingMap aRenamingMap_964;
    // $FF: synthetic field
    private final Renamer aRenamer_965;


    private DefaultHandler_Sub2(Renamer var1) {
        this.aRenamer_965 = var1;
        this.aRenamingMap_964 = new RenamingMap();
    }

    private void method1847() {
        try {
            Vector var1 = new Vector(this.aRenamingMap_964.method444());

            int var2;
            String var4;
            String var8;
            for (int var10000 = var2 = 0; var10000 < var1.size(); var10000 = var2) {
                String var3 = (String) var1.get(var2);
                var4 = this.aRenamingMap_964.get(var3);
                ClassGen var5;
                if ((var5 = Renamer.method300(this.aRenamer_965).getClassGen(var4)) != null) {
                    String[] var6 = var5.getInterfaceNames();

                    int var7;
                    for (var10000 = var7 = 0; var10000 < var6.length; var10000 = var7) {
                        var8 = var6[var7];
                        this.aRenamingMap_964.put(var8 + var3.substring(var3.indexOf("&")), var8);
                        ++var7;
                    }
                }

                ++var2;
            }

            Set var13 = this.aRenamingMap_964.method444();
            Vector var14 = new Vector(NameRepository.getConstantNamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).method444());

            Iterator var15;
            for (Iterator var16 = var15 = var13.iterator(); var16.hasNext(); var16 = var15) {
                var4 = (String) var15.next();
                String var17 = this.aRenamingMap_964.get(var4);

                Iterator var18;
                for (var16 = var18 = var14.iterator(); var16.hasNext(); var16 = var18) {
                    String var19;
                    if ((var19 = (String) var18.next()).startsWith(var17)) {
                        var8 = NameRepository.getConstantNamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).get(var19);
                        if (var4.substring(var4.indexOf("&") + 1, var4.lastIndexOf("&")).equals(var8)) {
                            String var10 = var4.substring(var4.lastIndexOf("&") + 1);
                            String var11 = var19.substring(var19.lastIndexOf("&") + 1);
                            if (var10.equals(MethodUtils.method1454(var11))) {
                                NameRepository.getConstantNamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).method440(var19);
                                NameRepository.getMethodRenamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).method440(var4);
                            }
                        }
                    }
                }
            }
        } catch (Exception var12) {
        }

    }

    private String method1848(String var1, RenamingMap var2) {
        String var3 = "";

        for (String var10000 = var1; var10000.length() > 0; var10000 = var1) {
            int var4 = var1.indexOf(76);
            int var5 = var1.indexOf(59);
            if (var4 != -1 && var5 != -1 && var4 < var5) {
                String var6 = var1.substring(var4 + 1, var5).replace('/', '.');
                if (var2.get(var6) != null) {
                    var6 = var2.get(var6);
                }

                var6 = var6.replace('.', '/');
                var3 = var3 + var1.substring(0, var4 + 1) + var6 + ";";
                var1 = var1.substring(var5 + 1);
            } else {
                var3 = var3 + var1;
                var1 = "";
            }
        }

        return var3;
    }

    private void method1849() {
        Iterator var2;
        String var4;
        String var5;
        Iterator var10000;
        for (var10000 = var2 = Class159.method1626(Class172.method1710(Renamer.method324(this.aRenamer_965))).method444().iterator(); var10000.hasNext(); var10000 = var2) {
            String var3;
            var4 = (var3 = (String) var2.next()).substring(var3.lastIndexOf("&") + 1);
            var5 = this.method1848(var4, Class165.method1653(Class172.method1708(Renamer.method324(this.aRenamer_965))));
            if (!var4.equals(var5)) {
                Class159.method1628(Class172.method1710(Renamer.method324(this.aRenamer_965))).put(var4, var5);
            }
        }

        Iterator var7;
        for (var10000 = var7 = NameRepository.getConstantNamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).method444().iterator(); var10000.hasNext(); var10000 = var7) {
            var5 = (var4 = (String) var7.next()).substring(var4.lastIndexOf("&") + 1);
            String var6 = this.method1848(var5, Class165.method1653(Class172.method1708(Renamer.method324(this.aRenamer_965))));
            if (!var5.equals(var6)) {
                NameRepository.method1534(Class172.method1707(Renamer.method324(this.aRenamer_965))).put(var4, var6);
            }
        }

    }

    private void method1850() throws Exception {
        XMLReader var2 = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        var2.setContentHandler(this);
        var2.parse(new InputSource(new FileReader(Packaging.method582())));
        this.method1849();
        this.method1847();
    }

    // $FF: synthetic method
    public static void method1851(DefaultHandler_Sub2 var0) throws Exception {
        var0.method1850();
    }

    // $FF: synthetic method
    public DefaultHandler_Sub2(Renamer var1, Class178 var2) {
        this(var1);
    }

    private void method1852() {
        String var1 = MethodUtils.getPackageName(this.oldClassName);
        String var2 = MethodUtils.getPackageName(this.newClassName);
        if (!Packaging.method578() || !Packaging.method576().equals(var2)) {
            int var3 = 0;

            int var4;
            int var10000;
            for (var10000 = var4 = var1.length() - 1; var10000 > 0; var10000 = var4) {
                if (var1.charAt(var4) == 46) {
                    ++var3;
                }

                --var4;
            }

            int var5;
            for (var10000 = var5 = var2.length() - 1; var10000 > 0; var10000 = var5) {
                if (var2.charAt(var5) == 46) {
                    --var3;
                }

                --var5;
            }

            if (var3 == 0) {
                Class141.method1520(Class172.method1711(Renamer.method324(this.aRenamer_965))).put(var1, var2);
                Class141.method1522(Class172.method1711(Renamer.method324(this.aRenamer_965))).put(var2, "&");

                for (String var6 = var1; var6.lastIndexOf(46) > 0; var6 = var1) {
                    var1 = var1.substring(0, var1.lastIndexOf(46));
                    var2 = var2.substring(0, var2.lastIndexOf(46));
                    Class141.method1520(Class172.method1711(Renamer.method324(this.aRenamer_965))).put(var1, var2);
                    Class141.method1522(Class172.method1711(Renamer.method324(this.aRenamer_965))).put(var2, "&");
                }

            }
        }
    }

    public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
        String packageName;
        if ("class".equals(var3)) {
            this.oldClassName = var4.getValue("old");
            this.newClassName = var4.getValue("new");
            if (Renamer.method299()) {
                packageName = MethodUtils.getPackageName(this.newClassName);
                if (Renamer.getRenamerMap().containsKey(packageName)) {
                    this.newClassName = Renamer.getRenamerMap().get(packageName) + this.newClassName.substring(packageName.length());
                }
            }

            Class165.method1653(Class172.method1708(Renamer.method324(this.aRenamer_965))).put(this.oldClassName, this.newClassName);
            Class165.method1655(Class172.method1708(Renamer.method324(this.aRenamer_965))).put(this.newClassName, this.oldClassName);
            this.method1852();
        } else {
            String var6;
            String var7;
            String var8;
            if ("method".equals(var3)) {
                packageName = var4.getValue("old");
                var6 = var4.getValue("new");
                var7 = packageName.substring(0, packageName.indexOf("("));
                var8 = packageName.substring(packageName.indexOf("("));
                NameRepository.getConstantNamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).put(this.oldClassName + "&" + var7 + "&" + var8, var6);
                String key = this.oldClassName + "&" + var6 + "&" + MethodUtils.method1454(var8);
                if (NameRepository.getMethodRenamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).containsKey(key)) {
                    this.aRenamingMap_964.put(key, this.oldClassName);
                }

                NameRepository.getMethodRenamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).put(key, "&");
            } else if ("field".equals(var3)) {
                packageName = var4.getValue("old");
                var6 = var4.getValue("new");
                var7 = packageName.substring(0, packageName.indexOf(" "));
                var8 = packageName.substring(packageName.indexOf(" ") + 1);
                Class159.method1626(Class172.method1710(Renamer.method324(this.aRenamer_965))).put(this.oldClassName + "&" + var7 + "&" + var8, var6);
                Class159.method1625(Class172.method1710(Renamer.method324(this.aRenamer_965))).put(this.oldClassName + "&" + var6 + "&" + var8, "&");
            } else if ("source".equals(var3)) {
                packageName = var4.getValue("old");
                var6 = var4.getValue("new");
                Renamer.method311(this.aRenamer_965).method495(packageName, var6);
            } else if ("line".equals(var3)) {
                Integer var10 = Integer.valueOf(var4.getValue("l"));
                Renamer.method311(this.aRenamer_965).method497().add(var10);
            } else if ("annotation".equals(var3)) {
                packageName = var4.getValue("oldClassName");
                var6 = var4.getValue("oldMethodName");
                var7 = var4.getValue("newMethodName");
                NameRepository.getSignatureNamingMap(Class172.method1707(Renamer.method324(this.aRenamer_965))).put(packageName + "&" + var6, var7);
            }
        }

    }
}
