package dataset;

import org.xml.sax.SAXException;

public class StateName extends StateTag {

	private StateObject stateObject;
	private StringBuffer stringBuffer;
	
	public StateName(StateObject stateObject) {
		this.stateObject = stateObject;
		this.stringBuffer = new StringBuffer();
	}
	
	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		
		context.popState();
	}

	@Override
	public void characters(XmlObjectExtractHandler context, char[] characters, int start, int length) throws SAXException {
		
		this.stringBuffer.append(characters, start, length);
		System.out.println(this.stringBuffer);
	}

}
