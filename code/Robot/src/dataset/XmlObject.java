package dataset;

import database.bean.AnnotatedObject;

public class XmlObject {
	
	private String label;
	private Polygon polygon;
	
	public XmlObject(String label, Polygon polygon) {
		this.label = label;
		this.polygon = polygon;
	}
	
	public AnnotatedObject toAnnotatedObject() {
		return null;
	}
	
	@Override
	public String toString() {
		return "XmlObject [label=" + label + ", polygon=" + polygon + "]";
	}
	
}
