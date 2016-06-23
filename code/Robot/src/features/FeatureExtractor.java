package features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.image.MonoImage;

public class FeatureExtractor {

	
	public static List<Feature> extract(MonoImage image) {
		
		List<Feature> features = new ArrayList<Feature>();
		
		Map<Position, List<Integer>> chainCodes = ChainCodeExtractor.extract(image);
		for(Position position : chainCodes.keySet()) {
			List<Integer> chainCode = chainCodes.get(position);
			Feature feature = new Feature(position, chainCode);
			features.add(feature);
		}
				
		return features;
	}
	
}
