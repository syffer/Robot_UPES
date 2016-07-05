package gui.model;

import java.awt.Color;
import java.util.List;

import features.ChainCode;
import features.Feature;

import image.MonoImage;
import image.Position;
import image.RGBImage;

/**
 * Represents an internal model that contains the results 
 * of the feature extraction performed on an image. 
 * 
 * Each feature will be represented by a color on an image. 
 * 
 * @see features.FeatureExtractor 
 * @author Maxime PINEAU
 */
public class FeatureExtractionModel extends ImageModel {
	
	private static final Color[] colors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.DARK_GRAY};
	
	protected MonoImage original;
	private List<Feature> features;
	
	/**
	 * Creates a new FeatureExtractionModel.
	 * Generate a new RGBImage where each feature will be colored 
	 * @param image the image on which the feature extraction has been performed 
	 * @param features the result of the feature extraction 
	 * @param executionTime the execution time the feature extraction has taken 
	 */
	public FeatureExtractionModel(MonoImage image, List<Feature> features, double executionTime) { 
		super(new RGBImage(image), image, "Feature Extraction", executionTime);
		
		this.original = image;
		this.features = features;
		
		int i = 0;
		for(Feature feature : this.features) {
			
			Color color = FeatureExtractionModel.colors[i % FeatureExtractionModel.colors.length];
			i++;
			
			Position origin = feature.getPosition();
			ChainCode chainCode = feature.getChainCode();
			
			for(Position position : chainCode.getPositions(origin)) {
				this.image.set(position.getI(), position.getJ(), color.getRGB());
			}
			
		}
		
	}
	
	public MonoImage getOriginalImage() {
		return this.original;
	}
	
	/**
	 * @return the extracted features 
	 */
	public List<Feature> getFeatures() {
		return this.features;
	}
		
	
}
