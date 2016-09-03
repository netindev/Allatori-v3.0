package com.allatori;

import java.io.Reader;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class StackTraceLogFileReader {
	
	/* OK */

	public static Class166 readLogFile(Reader reader) throws Exception {
		final XMLReader xml = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		final Class166 cl = new Class166();
		xml.setContentHandler(new PredefinedNamingHandler(cl));
		xml.parse(new InputSource(reader));
		return cl;
	}
}
