package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class StateTag {
	
	public void startDocument(XmlObjectExtractHandler context) {
		
	}
	
	public void endDocument(XmlObjectExtractHandler context) {
		
	}
	
	public void startElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs) throws SAXException {
		
	}
	
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
	}
	
	public void characters(XmlObjectExtractHandler context, char[] characters, int start, int length) throws SAXException {
		
	}
	
}
