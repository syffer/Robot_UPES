package dataset;

import org.xml.sax.SAXException;

public class StateBoxValue extends StateTag {
	
	public enum Coordonnee {
		XMIN, 
		XMAX, 
		YMIN, 
		YMAX
	}
	
	private StateBox stateBox;
	private Coordonnee coordonnee;
	private StringBuffer stringBuffer;
	
	public StateBoxValue(StateBox stateBox, Coordonnee coordonnee) {
		this.stateBox = stateBox;
		this.coordonnee = coordonnee;
		this.stringBuffer = new StringBuffer();
	}
	
	public void endElement(ExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		try {
			double value = Double.parseDouble(this.stringBuffer.toString());
						
			if(this.coordonnee == Coordonnee.XMIN) this.stateBox.setXmin(value);
			else if(this.coordonnee == Coordonnee.XMAX) this.stateBox.setXmax(value);
			else if(this.coordonnee == Coordonnee.YMIN) this.stateBox.setYmin(value);
			else if(this.coordonnee == Coordonnee.YMAX) this.stateBox.setYmax(value);
			
			context.popState();
			
		} catch(NumberFormatException nfe) {
			context.popState();
			context.popState();
			context.popState();
		}
	}
	
	public void characters(ExtractHandler context, char[] characters, int start, int length) throws SAXException {
		this.stringBuffer.append(characters, start, length);
	}
	
}
