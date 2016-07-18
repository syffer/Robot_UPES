package features;

import geometry.Position;
import image.MonoImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureExtractor {

	
	/**
	 * Extracts the features presents in a monochromatic image. 
	 * A feature is created from a starting position and an extracted chain code.
	 * @param image the monochromatic image 
	 * @return a list of the present features in the image 
	 */
	public static List<SomeObject> extract(MonoImage image) {
		
		List<SomeObject> someObjects = new ArrayList<SomeObject>();
		ChainCodeExtractor chainCodeExtractor = new ChainCodeExtractor();
		
		Map<Position, ChainCode> chainCodes = chainCodeExtractor.extract(image);
		for(Position position : chainCodes.keySet()) {
			ChainCode chainCode = chainCodes.get(position);
			SomeObject someObject = new SomeObject(position, chainCode);
			someObjects.add(someObject);
		}
				
		return someObjects;
	}
	
}
