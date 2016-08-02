package gui.model;

import java.awt.Color;
import java.util.List;

import features.ChainCode;
import features.PositionnedObject;
import geometry.Position;

import image.AbstractMonoImage;
import image.Image;
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
	
	protected AbstractMonoImage previous;
	private List<PositionnedObject> someObjects;
	
	/**
	 * Creates a new FeatureExtractionModel.
	 * Generate a new RGBImage where each feature will be colored 
	 * @param image the image on which the feature extraction has been performed 
	 * @param features the result of the feature extraction 
	 * @param executionTime the execution time the feature extraction has taken 
	 */
	public FeatureExtractionModel(AbstractMonoImage image, Image original, List<PositionnedObject> someObjects, double executionTime) { 
		super(new RGBImage(original), image, original, "Feature Extraction", executionTime);
		
		this.previous = image;
		this.someObjects = someObjects;
		
		int i = 0;
		for(PositionnedObject someObject : this.someObjects) {
			
			Color color = FeatureExtractionModel.colors[i % FeatureExtractionModel.colors.length];
			i++;
			
			Position origin = someObject.getPosition();
			ChainCode chainCode = someObject.getChainCode();
			
			for(Position position : chainCode.getPositions(origin)) {
				this.image.setRGB(position.getI(), position.getJ(), color.getRGB());
			}
			
		}
		
	}
	
	public AbstractMonoImage getPreviousImage() {
		return this.previous;
	}
	
	/**
	 * @return the extracted features 
	 */
	public List<PositionnedObject> getExtractedObjects() {
		return this.someObjects;
	}
		
	
}
