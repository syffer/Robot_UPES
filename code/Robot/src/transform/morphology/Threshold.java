package transform.morphology;

import transform.Transformation;
import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

/**
 * Applies a threshold to an image 
 * @author Maxime PINEAU
 * @see Transformation 
 */
public class Threshold extends Transformation {

	private int threshold;
	
	/**
	 * Creates a visitor allowing to apply a threshold to an image. 
	 * A pixel will be represented in black if its value is strictly under the threshold, and in white otherwise. 
	 * @param threshold the threshold values 
	 */
	public Threshold(int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Sets the threshold value 
	 * @param threshold the new threshold value 
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public void visit(RGBImage image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Pixel pixel = image.getPixel(i, j);
				int grey = pixel.getGrey();
				matrix[i][j] = (grey >= this.threshold) ? 255 : 0;
			}
		}
		
		this.setTransformedImage(new MonoImage(matrix, this.threshold));
	}

	@Override
	public void visit(GreyImage image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int grey = image.get(i, j);				
				matrix[i][j] = (grey >= this.threshold) ? 255 : 0;
			}
		}
		
		this.setTransformedImage(new MonoImage(matrix, this.threshold));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(image);
	}

	/*
	@Override
	public Threshold clone() {
		return (Threshold) super.clone();
	}
	*/
}
