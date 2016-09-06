package com.allatori.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.allatori.FileUtils;
import com.allatori.Obfuscate;
import com.allatori.ParseConfig;

public class ObfuscatorTask extends Task {

	private String aString951;

	@Override
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
					String var3 = FileUtils.method688(var1);
					var3 = this.getProject().replaceProperties(var3);
					FileUtils.method690(var2, var3);
					ParseConfig.parseConfigFile(var2.getPath());
					Obfuscate.execute();
				}
			} catch (final Exception var4) {
				throw new BuildException(var4);
			}
		}
	}

	public void method1824(String var1) {
		this.aString951 = var1;
	}
}
