package transform.morphology;

import transform.Transformation;
import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

public class Negative extends Transformation {

	@Override
	public void visit(RGBImage image) {
		RGBImage negativeImage = new RGBImage(image.getWidth(), image.getHeight());
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Pixel pixel = image.getPixel(i,  j);
				Pixel negative = new Pixel(255 - pixel.getRed(), 255 - pixel.getGreen(), 255 - pixel.getBlue());
				negativeImage.set(i, j, negative);
			}
		}
		
		this.setTransformedImage(negativeImage);
	}

	@Override
	public void visit(GreyImage image) {		
		GreyImage negativeImage = new GreyImage(image.getWidth(), image.getHeight());
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int negative = 255 - image.get(i, j);
				negativeImage.set(i, j, negative);
			}
		}
		
		this.setTransformedImage(negativeImage);
	}

	@Override
	public void visit(MonoImage image) {
		MonoImage negativeImage = new MonoImage(image.getWidth(), image.getHeight(), image.getThresholdValue());
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int negative = 255 - image.get(i, j);
				negativeImage.set(i, j, negative);
			}
		}
		
		this.setTransformedImage(negativeImage);
	}
	
	/*
	private int[][] apply(int[][] channel, int width, int height) {
		int[][] negativeChannel = new int[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				negativeChannel[i][j] =  255 - channel[i][j];
			}
		}
		
		return negativeChannel;
	}
	*/
}
