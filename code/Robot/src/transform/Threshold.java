package transform;

import java.awt.Color;

import model.image.GreyImageModel;
import model.image.MonoImageModel;
import model.image.RGBImageModel;

public class Threshold extends Transformation {

	private int threshold;
	
	public Threshold(int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public void apply(RGBImageModel image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Color color = new Color(image.get(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				matrix[i][j] = (grey >= this.threshold) ? 255 : 0;
			}
		}
		
		this.imageTransformed = new MonoImageModel(matrix);
	}

	@Override
	public void apply(GreyImageModel image) {
		
		int[][] matrix = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int grey = image.get(i, j);				
				matrix[i][j] = (grey >= this.threshold) ? 255 : 0;
			}
		}
		
		this.imageTransformed = new MonoImageModel(matrix);
	}

	@Override
	public void apply(MonoImageModel image) {
		
	}

}
