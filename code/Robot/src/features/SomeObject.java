package features;

import geometry.Position;

public class SomeObject {

	private Position origin;
	private ChainCode chainCode;
	private Feature feature;
	
	public SomeObject(Position origin, ChainCode chainCode) {
		this.origin = origin;
		this.chainCode = chainCode;
		this.feature = new Feature(chainCode);
	}
	
	public Position getPosition() {
		return origin;
	}
	
	public ChainCode getChainCode() {
		return chainCode;
	}
	
	public Feature getFeature() {
		return this.feature;
	}
	
}
