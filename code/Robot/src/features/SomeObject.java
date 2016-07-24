package features;

public abstract class SomeObject implements Comparable<SomeObject> {
	
	protected Feature feature;
	
	public SomeObject(Feature feature) {
		this.feature = feature;
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
	
}
