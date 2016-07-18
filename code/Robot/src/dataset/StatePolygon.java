package dataset;

import geometry.Point;
import geometry.Polygon;

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
		
		// don't add the point if it is already is in the polygon 
		// sometimes, LabelMe polygon has the same point twice (next to each other)
		if(this.polygon.hasPoints() && point.equals(this.polygon.getPoint(this.polygon.getNbPoints() - 1))) return;
		
		this.polygon.addPoint(point);
	}
	
	
	@Override
	public void startElement(ExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("pt")) context.pushState(new StatePt(this));
		
	}

	@Override
	public void endElement(ExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		if(!rawName.equals("polygon")) return;
		
		if(this.polygon.getNbPoints() <= 1) {
			context.popState();
			context.popState();
			return;
		}
		
		this.stateObject.setPolygon(this.polygon); 
		context.popState();
	}
	
}
