package com.allatori.watermark;

import com.allatori.Class167;
import com.allatori.Logger;
import com.allatori.clazz.ClassStorage;
import com.allatori.config.Configuration;
import com.allatori.obfuscate.Obfuscate;
import com.allatori.util.Info;

public class Watermark extends Configuration {

    private static void print() {
        System.out.println(Info.name() + " " + Info.version());
        System.out.println("Usage:");
        System.out.println("com.allatori.Watermark -add <config file>");
        System.out.println("or");
        System.out.println("com.allatori.Watermark -extract <config file>");
    }

    public static void main(String[] stringArray) {
        System.out.println(Configuration.printSplash());
        if (stringArray.length != 2) {
        	print();
            System.exit(0);
        }

        if ("-add".equals(stringArray[0])) {
            Class167.method1661(false);
        } else if ("-extract".equals(stringArray[0])) {
            Class167.method1661(true);
        } else {
        	print();
            System.exit(0);
        }

        Configuration.parseConfigFile(stringArray[1]);

        try {
            ClassStorage classStorage = Obfuscate.method1269();
            if (Class167.method1659()) {
                if (Class167.method1660() == null) {
                    Logger.printError("Configuration error. Watermark key is not set.");
                    System.exit(0);
                }

                System.out.println("Extracted watermark: \"" + Configuration.method1274(classStorage) + "\"");
            } else {
                if (Class167.method1660() == null) {
                    Logger.printError("Configuration error. Watermark key is not set.");
                    System.exit(0);
                }

                if (Class167.method1656() == null) {
                    Logger.printError("Configuration error. Watermark value is not set.");
                    System.exit(0);
                }

                Configuration.method1266(classStorage);
                Configuration.method1275(classStorage);
            }
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

    }
}
