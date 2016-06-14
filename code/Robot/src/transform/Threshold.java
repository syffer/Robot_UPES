package transform;

import java.awt.Color;

import model.image.GreyImage;
import model.image.MonoImage;
import model.image.RGBImage;

public class Threshold extends Transformation {

	private int threshold;
	
	public Threshold(int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public void apply(RGBImage image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Color color = new Color(image.get(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
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

}
