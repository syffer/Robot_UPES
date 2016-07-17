package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StateOther extends StateTag {

	@Override
	public void startDocument(XmlObjectExtractHandler context) {
		
	}

	@Override
	public void endDocument(XmlObjectExtractHandler context) {
		
	}

	@Override
	public void startElement(XmlObjectExtractHandler context, String nameSpace,
			String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("object")) {
			context.pushState(new StateObject());
		}
		
	}

	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace,
			String localName, String rawName) throws SAXException {
		
	}

	@Override
	public void characters(XmlObjectExtractHandler context, char[] characteres,
			int start, int length) throws SAXException {
		
	}

}
