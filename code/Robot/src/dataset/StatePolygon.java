package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StatePolygon extends StateTag {

	private StateObject stateObject;
	private Polygon polygon;
	
	public StatePolygon(StateObject stateObject) {
		this.stateObject = stateObject;
		this.polygon = new Polygon();
	}
	
	
	public void addPointToPolygon(Point point) {
		this.polygon.addPoint(point);
	}
	
	
	@Override
	public void startElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("pt")) context.pushState(new StatePt(this));
		
	}

	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		
		context.popState();
	}
	
}
