package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dataset.StatePtValue.Coordonnee;

public class StatePt extends StateTag {

	private StatePolygon statePolygon;
	private Point point;
	
	public StatePt(StatePolygon statePolygon) {
		this.statePolygon = statePolygon;
		this.point = new Point();
	}
	
	
	public void setX(int x) {
		this.point.x = x;
	}
	
	public void setY(int y) {
		this.point.y = y;
	}
	
	
	@Override
	public void startElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("x")) context.pushState(new StatePtValue(this, Coordonnee.X));
		else if(rawName.equals("y")) context.pushState(new StatePtValue(this, Coordonnee.Y));
	}

	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		this.statePolygon.addPointToPolygon(this.point);
		context.popState();
	}

}
