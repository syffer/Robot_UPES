package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class StateTag {
	
	public void startDocument(ExtractHandler context) {
		
	}
	
	public void endDocument(ExtractHandler context) {
		
	}
	
	public void startElement(ExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs) throws SAXException {
		
	}
	
	public void endElement(ExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
	}
	
	public void characters(ExtractHandler context, char[] characters, int start, int length) throws SAXException {
		
	}
	
}
