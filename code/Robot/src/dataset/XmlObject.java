package dataset;

import database.bean.AnnotatedObjectBean;
import features.Feature;
import geometry.Polygon;

public class XmlObject {
	
	private String label;
	private Polygon polygon;
	
	public XmlObject(String label, Polygon polygon) {
		this.label = label;
		this.polygon = polygon;
	}
	
	public AnnotatedObjectBean toAnnotatedObject() {
		
		Feature feature = new Feature(this.polygon);
		return new AnnotatedObjectBean(this.label, feature);
	}
	
	@Override
	public String toString() {
		return "XmlObject [label=" + label + ", polygon=" + polygon + "]";
	}
	
}
