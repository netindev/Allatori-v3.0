package com.allatori.stacktrace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.allatori.Class166;

public class StackTraceUtils {

    public static void translateStackTrace(BufferedReader inputReader, Class166 log, PrintWriter outputWriter) throws IOException {
        Pattern var3 = Pattern.compile("(.*(?:at|Frame)\\s+)(.*)\\((.*)\\)(.*)");
        Pattern var4 = Pattern.compile("(.*syslog:\\s+)(.*?)\\((.*)\\)(.*)");
        String var6 = null;
        String var7 = null;
        String var8 = null;
        String var9 = null;

        String line;
        for (BufferedReader var10000 = inputReader; (line = var10000.readLine()) != null; var10000 = inputReader) {
            boolean var11 = false;
            Matcher var10;
            if ((var10 = var3.matcher(line)).matches()) {
                var11 = true;
                var6 = var10.group(1);
                if ((var7 = var10.group(2)).indexOf(40) > 0) {
                    var7 = var7.substring(0, var7.indexOf(40));
                }

                var8 = var10.group(3);
                var9 = var10.group(4);
            }

            if (!var11 && (var10 = var4.matcher(line)).matches()) {
                var11 = true;
                var6 = var10.group(1);
                var7 = var10.group(2);
                var8 = var10.group(3);
                var9 = var10.group(4);
            }

            if (var11 && log.aRenamingMap_852.containsKey(var7)) {
                String var12 = log.aRenamingMap_852.get(var7);
                boolean var13 = false;
                if (var8.startsWith("(Compiled Method)")) {
                    var13 = true;
                    var8 = var8.substring("(Compiled Method)".length());
                }

                if (var8.indexOf(58) >= 0) {
                    String var14 = var8.substring(0, var8.indexOf(58));
                    String var15 = var8.substring(var8.indexOf(58) + 1);
                    if (log.aRenamingMap_854.containsKey(var14)) {
                        var14 = log.aRenamingMap_854.get(var14);
                    }

                    try {
                        int var16;
                        if ((var16 = log.aVector853.indexOf(Integer.valueOf(var15))) >= 0) {
                            var15 = "" + var16;
                        }
                    } catch (NumberFormatException var17) {
                    }

                    var8 = var14 + ":" + var15;
                } else if (log.aRenamingMap_854.containsKey(var8)) {
                    var8 = log.aRenamingMap_854.get(var8);
                }

                line = var6 + var12 + (var13 ? "(Compiled Method)" : "") + "(" + var8 + ")" + var9;
            }

            outputWriter.println(line);
        }

    }
}
