package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StateObject extends StateTag {

	private String name;
	private Polygon polygon; 
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	
	
	@Override
	public void startElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("name")) context.pushState(new StateName(this));
		else if(rawName.equals("polygon")) 	context.pushState(new StatePolygon(this));	
		else if(rawName.equals("box")) context.pushState(new StateBox(this));
	}
	
	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		if(!rawName.equals("object")) return;
		
		XmlObject xmlObject = new XmlObject(this.name, this.polygon);
		context.addXmlObject(xmlObject);
		
		context.popState();
	}
	
}
