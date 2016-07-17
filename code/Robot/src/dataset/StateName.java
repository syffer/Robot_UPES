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
	public void endElement(ExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {

		String name = this.stringBuffer.toString();
		
		if(context.isLabelResearched(name)) { 
			this.stateObject.setName(name);
			context.popState();
		} else { 
			context.popState(); // cancel StateName
			context.popState(); // cancel StateObject 
		}
		
	}

	@Override
	public void characters(ExtractHandler context, char[] characters, int start, int length) throws SAXException { 
		this.stringBuffer.append(characters, start, length);
	}

}
