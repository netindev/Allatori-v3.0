package com.allatori;

import java.io.Reader;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class StackTraceLogFileReader {
	
	/* OK */

	public static StackTraceLog readLogFile(Reader reader) throws Exception {
		final XMLReader xml = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		final StackTraceLog cl = new StackTraceLog();
		xml.setContentHandler(new PredefinedNamingHandler(cl));
		xml.parse(new InputSource(reader));
		return cl;
	}
}
