package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class StateTag {
	public abstract void startDocument(XmlObjectExtractHandler context);
	public abstract void endDocument(XmlObjectExtractHandler context);
	public abstract void startElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs) throws SAXException;
	public abstract void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException;
	public abstract void characters(XmlObjectExtractHandler context, char[] characteres, int start, int length) throws SAXException;
	
}
