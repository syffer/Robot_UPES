package features;

import geometry.Position;

public class PositionnedObject extends SomeObject {

	private Position origin;
	private ChainCode chainCode;
	
	public PositionnedObject(Position origin, ChainCode chainCode) {
		super(new Feature(chainCode));
		this.origin = origin;
		this.chainCode = chainCode;
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
