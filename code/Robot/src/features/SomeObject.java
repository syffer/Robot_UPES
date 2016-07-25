package features;

public abstract class SomeObject implements Comparable<SomeObject> {
	
	protected Feature feature;
	protected Tag tag;
	
	public SomeObject(Feature feature) {
		this(feature, null);
	}
	
	public SomeObject(Feature feature, Tag tag) {
		this.feature = feature;
		this.tag = tag;
	}
	

	@Override
	public int compareTo(SomeObject other) { 
		return this.feature.compareTo(other.feature);
	}


	@Override
	public String toString() {
		return "SomeObject [feature=" + feature + "]";
	}
	
	public Feature getFeature() {
		return this.feature;
	}
	
	public Tag getTag() {
		return this.tag;
	}
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public boolean isTagged() {
		return this.tag != null;
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
