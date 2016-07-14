package database.bean;

public class AnnotatedObject {
	
	public enum Tag {
		PERSON, 
		ROCK, 
		TREE, 
		CAR, 
		OTHER
	}
	
	public Integer id;
	public Tag tag;
	
	public double area; 
	public double perimeter;
	
	public double compactness;
	public double circularity;
	public int curvature; 
	public double bendingEnergy;
	
	public int width;
	public int height;
	public double ratioWidthHeight;
	
	public AnnotatedObject(Tag tag, double area, double perimeter, double compactness, double circularity, int curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this(-1, tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight);
	}
	
	public AnnotatedObject(int id, Tag tag, double area, double perimeter, double compactness, double circularity, int curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this.id = id;
		this.tag = tag;
		this.area = area;
		this.perimeter = perimeter;
		this.compactness = compactness;
		this.circularity = circularity;
		this.curvature = curvature;
		this.bendingEnergy = bendingEnergy;
		this.width = width;
		this.height = height;
		this.ratioWidthHeight = ratioWidthHeight;
	}

	
	public Tag getTag() {
		return tag;
	}


	public double getArea() {
		return area;
	}


	public double getPerimeter() {
		return perimeter;
	}


	public double getCompactness() {
		return compactness;
	}


	public double getCircularity() {
		return circularity;
	}


	public int getCurvature() {
		return curvature;
	}


	public double getBendingEnergy() {
		return bendingEnergy;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public double getRatioWidthHeight() {
		return ratioWidthHeight;
	}
	
}
