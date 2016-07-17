package dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StateOther extends StateTag {
	
	@Override
	public void startElement(ExtractHandler context, String nameSpace, String localName, String rawName, Attributes attributs)
			throws SAXException {
		
		if(rawName.equals("object")) {
			context.pushState(new StateObject());
		}
		
	}
		
}
