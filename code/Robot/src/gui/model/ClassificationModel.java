package gui.model;

import features.ChainCode;
import features.Feature;
import features.Position;
import image.MonoImage;
import image.RGBImage;

import java.awt.Color;
import java.util.List;

public class ClassificationModel extends ImageModel {
	
	private static final Color[] colors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.LIGHT_GRAY, Color.DARK_GRAY};
	
	protected MonoImage original;
	private List<Feature> features; 
	private int nbClasses;
	private int[] classes;
	
	public ClassificationModel(MonoImage image, List<Feature> features, int nbClasses, int[] classes, double executionTime) { 
		super(new RGBImage(image), image, "Classification (" + nbClasses + ")", executionTime);
		
		this.original = image;
		this.features = features;
		this.nbClasses = nbClasses;
		this.classes = classes;
		
		for(int i = 0; i < this.features.size(); i++) {
			Feature feature = this.features.get(i);
			int classe = this.classes[i];
			
			Color color = ClassificationModel.colors[classe % ClassificationModel.colors.length];
			
			Position origin = feature.getPosition();
			ChainCode chainCode = feature.getChainCode();
			
			for(Position position : chainCode.getPositions(origin)) {
				this.image.set(position.getI(), position.getJ(), color.getRGB());
			}
			
		}
		
	}
}
