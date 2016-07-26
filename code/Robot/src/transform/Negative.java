package transform;

import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

public class Negative extends Transformation {

	@Override
	public void apply(RGBImage image) {
		RGBImage negativeImage = new RGBImage(image.getWidth(), image.getHeight());
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Pixel pixel = new Pixel(image.get(i,  j));
				Pixel negative = new Pixel(255 - pixel.getRed(), 255 - pixel.getGreen(), 255 - pixel.getBlue());
				negativeImage.set(i, j, negative.getRGB());
			}
		}
		
		this.imageTransformed = negativeImage;
	}

	@Override
	public void apply(GreyImage image) {		
		GreyImage negativeImage = new GreyImage(image.getWidth(), image.getHeight());
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int negative = 255 - image.get(i, j);
				negativeImage.set(i, j, negative);
			}
		}
		
		this.imageTransformed = negativeImage;
	}

	@Override
	public void apply(MonoImage image) {
		MonoImage negativeImage = new MonoImage(image.getWidth(), image.getHeight());
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int negative = 255 - image.get(i, j);
				negativeImage.set(i, j, negative);
			}
		}
		
		this.imageTransformed = negativeImage;
	}
	
	private int[][] apply(int[][] channel, int width, int height) {
		int[][] negativeChannel = new int[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				negativeChannel[i][j] =  255 - channel[i][j];
			}
		}
		
		return negativeChannel;
	}
}
