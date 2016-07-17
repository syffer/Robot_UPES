package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StateName extends StateTag {

	private StateObject stateObject;
	private StringBuffer stringBuffer;
	
	public StateName(StateObject stateObject) {
		this.stateObject = stateObject;
		this.stringBuffer = new StringBuffer();
	}
	
	@Override
	public void startDocument(XmlObjectExtractHandler context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument(XmlObjectExtractHandler context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(XmlObjectExtractHandler context, String nameSpace,
			String localName, String rawName, Attributes attributs)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace,
			String localName, String rawName) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void characters(XmlObjectExtractHandler context, char[] characteres,
			int start, int length) throws SAXException {
		
		this.stringBuffer.append(characteres, start, length);
		System.out.println(this.stringBuffer);
	}

}
