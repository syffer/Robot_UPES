package transform;

import model.image.GreyImage;
import model.image.MonoImage;
import model.image.Pixel;
import model.image.RGBImage;

public class Threshold extends Transformation {

	private int threshold;
	
	public Threshold(int threshold) {
		this.threshold = threshold;
	}
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public void apply(RGBImage image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Pixel pixel = new Pixel(image.get(i, j));
				int grey = pixel.getGrey();
				matrix[i][j] = (grey >= this.threshold) ? 255 : 0;
			}
		}
		
		this.imageTransformed = new MonoImage(matrix);
	}

	@Override
	public void apply(GreyImage image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int grey = image.get(i, j);				
				matrix[i][j] = (grey >= this.threshold) ? 255 : 0;
			}
		}
		
		this.imageTransformed = new MonoImage(matrix);
	}

	@Override
	public void apply(MonoImage image) {
		
	}

	
	public MonoImage getTransformedImage() {
		return (MonoImage) this.imageTransformed;
	}
	
}
