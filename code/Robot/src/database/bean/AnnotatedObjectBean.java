package database.bean;

import features.Feature;
import features.Tag;

public class AnnotatedObjectBean {
	
	public Integer id;
	public Tag tag;
	private Feature feature;
	
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
		this.id = id;
		this.tag = Tag.getTag(tag); 
		this.feature = feature;
	}
	
	public Tag getTag() {
		return tag;
	}


	public double getArea() {
		return this.feature.getArea();
	}


	public double getPerimeter() {
		return this.feature.getPerimeter();
	}


	public double getCompactness() {
		return this.feature.getCompactness();
	}


	public double getCircularity() {
		return this.feature.getCircularity();
	}


	public double getCurvature() {
		return this.feature.getCurvature();
	}


	public double getBendingEnergy() {
		return this.feature.getBendingEnergy();
	}


	public int getWidth() {
		return this.feature.getWidth();
	}


	public int getHeight() {
		return this.feature.getHeight();
	}


	public double getRatioWidthHeight() {
		return this.feature.getRatioWidthHeight();
	}
	
}
