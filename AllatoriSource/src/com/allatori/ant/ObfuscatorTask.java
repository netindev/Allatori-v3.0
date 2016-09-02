package com.allatori.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.allatori.FileUtils;
import com.allatori.Obfuscate;
import com.allatori.ParseConfig;

public class ObfuscatorTask extends Task {
	
	/* OK */

	private String configurationFile;

	@Override
	public void execute() throws BuildException {
		if (this.configurationFile == null) {
			throw new BuildException("Missing \'config\' attribute.");
		} else {
			try {
				File file = new File(this.configurationFile);
				if (!file.canRead()) {
					throw new BuildException("Cannot find configuration file: \'" + this.configurationFile + "\'");
				} else {
					File nwFile = new File(this.configurationFile + ".resolved");
					nwFile.deleteOnExit();
					String read = FileUtils.read(file);
					read = this.getProject().replaceProperties(read);
					FileUtils.write(nwFile, read);
					ParseConfig.parseConfigFile(nwFile.getPath());
					Obfuscate.execute();
				}
			} catch (final Exception e) {
				throw new BuildException(e);
			}
		}
	}

	public void setConfigFile(String toSet) {
		this.configurationFile = toSet;
	}
}
