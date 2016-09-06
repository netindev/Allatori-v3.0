package com.allatori;

import java.io.FileReader;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ConfigFile implements IParse {

	private final String fileName;

	@Override
	public void parse() throws TemplateException {
		try {
			final FileReader fileReader = new FileReader(this.fileName);
			XMLReader reader;
			(reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader())
					.setContentHandler(new ConfigFileHandler(this, null));
			reader.parse(new InputSource(fileReader));
		} catch (final SAXException var4) {
			throw new TemplateException(var4.getMessage());
		} catch (final Exception var5) {
			throw new TemplateException(var5);
		}
	}

	public ConfigFile(String var1) {
		this.fileName = var1;
	}

	// $FF: synthetic method
	public static String getFileName(ConfigFile var0) {
		return var0.fileName;
	}
}
