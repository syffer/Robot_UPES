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

public class ExtractHandler extends DefaultHandler {

	private Set<String> researchedLabels;
	private List<XmlObject> xmlObjects;
	
	private Stack<StateTag> previousTags; 
	private StateTag currentTag;
	private int depth;
	
	public ExtractHandler(String... labels) {
		this(new HashSet<String>(Arrays.asList(labels)));
	}
	
	public ExtractHandler(Set<String> labels) {
		this.researchedLabels = labels;
		this.xmlObjects = new ArrayList<XmlObject>();
		
		this.previousTags = new Stack<StateTag>();
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
	
	

	public void addXmlObject(XmlObject xmlObject) {
		this.xmlObjects.add(xmlObject);
	}
	
	public List<XmlObject> getXmlObjects() {
		return this.xmlObjects;
	}
		
	public boolean isLabelResearched(String label) {
		return this.researchedLabels.contains(label);
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
	public void characters(char[] characters, int start, int length) throws SAXException { 
		this.currentTag.characters(this, characters, start, length);
	}
	
}
