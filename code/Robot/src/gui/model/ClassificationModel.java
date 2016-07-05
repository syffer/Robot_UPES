package gui.model;

import features.ChainCode;
import features.Feature;
import image.MonoImage;
import image.Position;
import image.RGBImage;

import java.awt.Color;
import java.util.List;

/**
 * Represents an internal model that contains the results 
 * of the classification performed on the extracted features of an image. 
 * 
 * Each cluster will be represented by a color on an image. 
 * 
 * @see classification.KMeans
 * @author Maxime PINEAU
 */
public class ClassificationModel extends ImageModel {
	
	private static final Color[] colors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.LIGHT_GRAY, Color.DARK_GRAY};
	
	protected MonoImage original;
	private List<Feature> features; 
	private int nbClasses;
	private int[] classes;
	
	/**
	 * Creates a new ClassificationModel. 
	 * Generate a new RGBImage where each cluster/class will be represented with a color.
	 * @param image the image on which the feature extraction has been performed 
	 * @param features the result of the feature extraction 
	 * @param nbClasses the number of classes used to perform the classification 
	 * @param classes an array that gives for each feature her corresponding class
	 * @param executionTime the execution time the classification has taken 
	 */
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
