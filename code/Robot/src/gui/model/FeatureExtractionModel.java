package gui.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import features.ChainCode;
import features.Feature;
import features.Position;

import image.MonoImage;
import image.RGBImage;

public class FeatureExtractionModel extends ImageModel {
	
	private static final Color[] colors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.DARK_GRAY};
	
	protected MonoImage original;
	private List<Feature> features;
	
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
	
	public List<Feature> getFeatures() {
		return this.features;
	}
		
	public BufferedImage getBufferedImage() {
		return this.image.getBufferedImage();
	}
	
	public double getExecutionTime() {
		return this.executionTime / 1000;
	}
	
}
