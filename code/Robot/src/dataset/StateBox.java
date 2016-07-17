package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dataset.StateBoxValue.Coordonnee;

public class StateBox extends StateTag {

	private double xmin;
	private double xmax;
	private double ymin;
	private double ymax;
	
	private StateObject stateObject;
	
	public StateBox(StateObject stateObject) {
		this.stateObject = stateObject;
	}
	
		
	public void setXmax(double xmax) {
		this.xmax = xmax;
	}
	
	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

	
	public void startElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs) throws SAXException {
		if(rawName.equals("xmin")) context.pushState(new StateBoxValue(this, Coordonnee.XMIN));
		else if(rawName.equals("xmax")) context.pushState(new StateBoxValue(this, Coordonnee.XMAX));
		else if(rawName.equals("ymin")) context.pushState(new StateBoxValue(this, Coordonnee.YMIN));
		else if(rawName.equals("ymax")) context.pushState(new StateBoxValue(this, Coordonnee.YMAX));
	}

	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		Polygon polygon = new Polygon();
		polygon.addPoint((int) this.xmin, (int) this.ymin);
		polygon.addPoint((int) this.xmin, (int) this.ymax);
		polygon.addPoint((int) this.xmax, (int) this.ymax);
		polygon.addPoint((int) this.xmax, (int) this.ymin);
		
		this.stateObject.setPolygon(polygon);
		
		context.popState();
	}
	
}
