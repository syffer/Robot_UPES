package gui.model;

import features.ChainCode;
import features.PositionnedObject;
import geometry.Position;
import image.Image;
import image.MonoImage;
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
	
	protected MonoImage previous;
	private List<PositionnedObject> someObjects; 
	//private int nbClasses;
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
	public ClassificationModel(MonoImage image, Image original, List<PositionnedObject> someObjects, int nbClasses, int[] classes, double executionTime) { 
		super(new RGBImage(original), image, original, "Classification (" + nbClasses + ")", executionTime);
		
		this.previous = image;
		this.someObjects = someObjects;
		//this.nbClasses = nbClasses;
		this.classes = classes;
		
		for(int i = 0; i < this.someObjects.size(); i++) {
			PositionnedObject someObject = this.someObjects.get(i);
			int classe = this.classes[i];
			
			Color color = ClassificationModel.colors[classe % ClassificationModel.colors.length];
			
			Position origin = someObject.getPosition();
			ChainCode chainCode = someObject.getChainCode();
			
			for(Position position : chainCode.getPositions(origin)) {
				this.image.setRGB(position.getI(), position.getJ(), color.getRGB());
			}
			
		}
		
	}
}
