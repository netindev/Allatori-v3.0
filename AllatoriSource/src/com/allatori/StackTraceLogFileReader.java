package com.allatori;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import java.io.Reader;

public class StackTraceLogFileReader {

    public static Class166 readLogFile(Reader var0) throws Exception {
        XMLReader var2 = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        Class166 var3 = new Class166();
        var2.setContentHandler(new PredefinedNamingHandler(var3));
        var2.parse(new InputSource(var0));
        return var3;
    }
}
