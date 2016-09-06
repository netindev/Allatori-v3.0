package com.allatori;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import java.io.FileReader;

public class ConfigFile implements Interface26 {

    private String fileName;


    public void parse() throws TemplateException {
        try {
            FileReader fileReader = new FileReader(this.fileName);
            XMLReader reader;
            (reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader()).setContentHandler(new ConfigFileHandler(this, null));
            reader.parse(new InputSource(fileReader));
        } catch (SAXException_Sub1 var4) {
            throw new TemplateException(var4.getMessage());
        } catch (Exception var5) {
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
