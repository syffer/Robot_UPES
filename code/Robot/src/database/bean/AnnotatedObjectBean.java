package database.bean;

import features.Feature;

public class AnnotatedObjectBean {
	
	public enum Tag {
		PERSON, 
		ROCK, 
		TREE, 
		CAR, 
		OTHER
	}
	
	public Integer id;
	public Tag tag;
	private Feature feature;
	
	public AnnotatedObjectBean(Tag tag, double area, double perimeter, double compactness, double circularity, int curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this(-1, tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight);
	}
	
	public AnnotatedObjectBean(int id, Tag tag, double area, double perimeter, double compactness, double circularity, int curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this(id, tag, new Feature(area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight));
	}

	public AnnotatedObjectBean(Tag tag, Feature feature) {
		this(-1, tag, feature);
	}
	
	public AnnotatedObjectBean(int id, Tag tag, Feature feature) {
		this.id = id;
		this.tag = tag; 
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
