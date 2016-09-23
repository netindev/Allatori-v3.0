package com.allatori.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.allatori.FileUtils;
import com.allatori.Obfuscate;
import com.allatori.ParseConfig;

public class ObfuscatorTask extends Task {
	
	/* OK */

	private String file;

	@Override
	public void execute() throws BuildException {
		if (this.file == null) {
			throw new BuildException("Missing \'config\' attribute.");
		} else {
			try {
				File toRead = new File(this.file);
				if (!toRead.canRead()) {
					throw new BuildException("Cannot find configuration file: \'" + this.file + "\'");
				} else {
					File resolved = new File(this.file + ".resolved");
					resolved.deleteOnExit();
					String read = FileUtils.readFile(toRead);
					read = this.getProject().replaceProperties(read);
					FileUtils.writeFile(resolved, read);
					ParseConfig.parseConfigFile(resolved.getPath());
					Obfuscate.execute();
				}
			} catch (final Exception e) {
				throw new BuildException(e);
			}
		}
	}

	public void setFile(String filePath) {
		this.file = filePath;
	}
}
