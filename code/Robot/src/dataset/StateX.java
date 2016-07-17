package dataset;

import org.xml.sax.SAXException;

public class StateX extends StateTag {

	private StatePt statePt;
	private StringBuffer stringBuffer;
	
	public StateX(StatePt statePt) {
		this.statePt = statePt;
		this.stringBuffer = new StringBuffer();
	}
	

	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		try {
			int x = Integer.parseInt(this.stringBuffer.toString());
			this.statePt.setX(x);
			
			context.popState();
			
		} catch(NumberFormatException nfe) {
			context.popState(); // cancel StateX, return to StatePt
			context.popState();	// cancel StatePt, return to StatePolygon 
			context.popState(); // cancel StatePolygon, return to StateObject 
			context.popState(); // cancel StateObject 
		}
			
	}

	@Override
	public void characters(XmlObjectExtractHandler context, char[] characters, int start, int length) throws SAXException {
		this.stringBuffer.append(characters, start, length);
	}

}
