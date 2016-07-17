package dataset;

import org.xml.sax.SAXException;

public class StatePtValue extends StateTag {

	public enum Coordonnee {
		X, 
		Y
	}
	
	private StatePt statePt;
	private Coordonnee coordonnee;
	private StringBuffer stringBuffer;
	
	public StatePtValue(StatePt statePt, Coordonnee coordonnee) {
		this.statePt = statePt;
		this.coordonnee = coordonnee;
		this.stringBuffer = new StringBuffer();
	}
	

	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		try {
			int value = Integer.parseInt(this.stringBuffer.toString());
			
			if(this.coordonnee == Coordonnee.X)	this.statePt.setX(value);
			else if(this.coordonnee == Coordonnee.Y)this.statePt.setY(value); 
			
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
