package database.bean;

import features.Feature;
import features.SomeObject;
import features.Tag;

public class AnnotatedObjectBean extends SomeObject {
	
	public Integer id;
	
	public AnnotatedObjectBean(String tag, double area, double perimeter, double compactness, double circularity, double curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this(-1, tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight);
	}
	
	public AnnotatedObjectBean(int id, String tag, double area, double perimeter, double compactness, double circularity, double curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this(id, tag, new Feature(area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight));
	}

	public AnnotatedObjectBean(String tag, Feature feature) {
		this(-1, tag, feature);
	}
	
	public AnnotatedObjectBean(int id, String tag, Feature feature) {
		super(feature, Tag.getTag(tag));
	}
		
}
