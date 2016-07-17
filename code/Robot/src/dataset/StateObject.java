package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StateObject extends StateTag {

	private String name;
	private Polygon polygon; 
	
	@Override
	public void startElement(XmlObjectExtractHandler context, String nameSpace,
			String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("name")) context.pushState(new StateName(this));
		else if(rawName.equals("polygon")) context.pushState(new StatePolygon(this));
		
	}
	
	@Override
	public void endElement(XmlObjectExtractHandler context, String nameSpace, String localName, String rawName) throws SAXException {
		
		
		context.popState();
	}
	
}
