package dataset;

import database.bean.AnnotatedObject;

public class XmlObject {
	
	private String tag;
	private Polygon polygon;
	
	public XmlObject(String tag, Polygon polygon) {
		this.tag = tag;
		this.polygon = polygon;
	}
	
	public AnnotatedObject toAnnotatedObject() {
		return null;
	}
	
}
