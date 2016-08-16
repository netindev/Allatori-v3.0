package com.allatori.obfuscate;

import com.allatori.Class167;
import com.allatori.LogFile;
import com.allatori.Logger;
import com.allatori.clazz.ClassStorage;
import com.allatori.config.Configuration;
import com.allatori.util.Info;

public class Obfuscate extends Configuration {

    public static void main(String[] arguments) {
        if (arguments.length > 1 && "-silent".equals(arguments[1])) {
            Logger.setSilentExecution(true);
        } else {
            System.out.println(Configuration.printSplash());
        }

        if (arguments.length != 1 && !Logger.silentExecution()) {
            printUsage();
            System.exit(0);
        }

        Configuration.parseConfigFile(arguments[0]);

        try {
            execute();
        } catch (Exception var2) {
            Logger.printError(var2.getMessage());
            System.out.println("############### EXCEPTION ###############");
            var2.printStackTrace();
            System.out.println("#########################################");
            System.exit(-1);
        }

    }

    private static void method1276(ClassStorage var0) throws Exception {
        (new Obfuscator(var0)).method1526();
    }

    private static void printUsage() {
        System.out.println(Info.name() + " " + Info.version());
        System.out.println("Usage:");
        System.out.println("com.allatori.Obfuscate <config file>");
    }

    public static void execute() throws Exception {
        ClassStorage var0;
        method1276(var0 = Configuration.method1269());
        if (Class167.method1660() != null && Class167.method1656() != null) {
            Configuration.method1266(var0);
        }

        Configuration.method1275(var0);
        Logger.printInfo("Obfuscation completed. Writing log file...");
        LogFile.writeLogFile();
    }
}
