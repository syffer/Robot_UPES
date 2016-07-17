package dataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlObjectExtractHandler extends DefaultHandler {

	private Set<String> tags;
	private List<XmlObject> xmlObjects;
	
	private Stack<StateTag> previousTags; 
	private StateTag currentTag;
	private int depth;
	
	public XmlObjectExtractHandler(String... tags) {
		this(new HashSet<String>(Arrays.asList(tags)));
	}
	
	public XmlObjectExtractHandler(Set<String> tags) {
		this.tags = tags;
		this.xmlObjects = new ArrayList<XmlObject>();
		
		this.previousTags = new Stack<StateTag>();
	}
	

	public List<XmlObject> getXmlObjects() {
		return this.xmlObjects;
	}
	
	public void pushState(StateTag stateTag) {
		this.previousTags.push(this.currentTag);
		this.currentTag = stateTag;
		this.depth++;
	}
	
	public void popState() {
		this.currentTag = this.previousTags.pop();
		this.depth--;
	}
	
	
	
	@Override
	public void startDocument() {
		this.xmlObjects = new ArrayList<XmlObject>();
		this.depth = 0;
		
		this.currentTag = new StateOther();
		this.currentTag.startDocument(this);
	}
	
	@Override
	public void endDocument() {
		this.currentTag.endDocument(this);
	}
	
	@Override
	public void startElement(String nameSpace, String localName, String rawName, Attributes attributs) throws SAXException {
		this.currentTag.startElement(this, nameSpace, localName, rawName, attributs);		
	}

	@Override
	public void endElement(String nameSpace, String localName, String rawName) throws SAXException {
		this.currentTag.endElement(this, nameSpace, localName, rawName);
	}

	@Override
	public void characters(char[] characteres, int start, int length) throws SAXException { 
		this.currentTag.characters(this, characteres, start, length);
	}
	
}
