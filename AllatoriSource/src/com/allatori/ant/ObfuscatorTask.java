package com.allatori.ant;

import com.allatori.Class55;
import com.allatori.Class90;
import com.allatori.Obfuscate;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;

public class ObfuscatorTask extends Task {

    private String aString951;


    public void execute() throws BuildException {
        if (this.aString951 == null) {
            throw new BuildException("Missing \'config\' attribute.");
        } else {
            try {
                File var1;
                if (!(var1 = new File(this.aString951)).canRead()) {
                    throw new BuildException("Cannot find configuration file: \'" + this.aString951 + "\'");
                } else {
                    File var2;
                    (var2 = new File(this.aString951 + ".resolved")).deleteOnExit();
                    String var3 = Class55.method688(var1);
                    var3 = this.getProject().replaceProperties(var3);
                    Class55.method690(var2, var3);
                    Class90.parseConfigFile(var2.getPath());
                    Obfuscate.execute();
                }
            } catch (Exception var4) {
                throw new BuildException(var4);
            }
        }
    }

    public void method1824(String var1) {
        this.aString951 = var1;
    }
}
