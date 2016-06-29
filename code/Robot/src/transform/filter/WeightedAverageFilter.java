package transform.filter;

import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

public class WeightedAverageFilter extends AbstractFilter {

	private static final int[][] originalMask = {{1, 1, 1}, 
										 		 {1, 8, 1}, 
										 		 {1, 1, 1}};
	
	private final int[][] mask;
	
	public WeightedAverageFilter(int[][] mask) {
		this.mask = mask;
	}
	
	public WeightedAverageFilter() {
		this(WeightedAverageFilter.originalMask);
	}
	
	
	
	@Override
	public void apply(RGBImage image) {
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				

				int nbPixels = 9;
				
				if((i == 1 || i == image.getWidth() - 2) && (j == 1 || j == image.getHeight() - 2) ) nbPixels = 11;
				else if((i == 1 || i == image.getWidth() - 2) || (j == 1 || j == image.getHeight() - 2)) nbPixels = 13;
				else nbPixels = 16;
				

				Pixel pixelTopLeft = new Pixel(image.get(i-1,  j-1));
				Pixel pixelTop = new Pixel(image.get(i,  j-1));
				Pixel pixelTopRight = new Pixel(image.get(i+1,  j-1));
				
				Pixel pixelLeft = new Pixel(image.get(i-1,  j));
				Pixel pixelMiddle = new Pixel(image.get(i,  j));
				Pixel pixelRight = new Pixel(image.get(i+1,  j));
				
				Pixel pixelBottomLeft = new Pixel(image.get(i-1,  j+1));
				Pixel pixelBottom = new Pixel(image.get(i,  j+1));
				Pixel pixelBottomRight = new Pixel(image.get(i+1,  j+1));
				
				int red = mask[0][0] * pixelTopLeft.getRed() + mask[0][1] * pixelTop.getRed() + mask[0][2] * pixelTopRight.getRed() + 
						  mask[1][0] * pixelLeft.getRed()   + mask[1][1] * pixelMiddle.getRed()   + mask[1][2] * pixelRight.getRed() +
						  mask[2][0] * pixelBottomLeft.getRed() + mask[2][1] * pixelBottom.getRed() + mask[2][2] * pixelBottomRight.getRed();
				
				int green = mask[0][0] * pixelTopLeft.getGreen() + mask[0][1] * pixelTop.getGreen() + mask[0][2] * pixelTopRight.getGreen() + 
							mask[1][0] * pixelLeft.getGreen()   + mask[1][1] * pixelMiddle.getGreen()   + mask[1][2] * pixelRight.getGreen() +
							mask[2][0] * pixelBottomLeft.getGreen() + mask[2][1] * pixelBottom.getGreen() + mask[2][2] * pixelBottomRight.getGreen();
				
				int blue = mask[0][0] * pixelTopLeft.getBlue() + mask[0][1] * pixelTop.getBlue() + mask[0][2] * pixelTopRight.getBlue() + 
						   mask[1][0] * pixelLeft.getBlue()   + mask[1][1] * pixelMiddle.getBlue()   + mask[1][2] * pixelRight.getBlue() +
						   mask[2][0] * pixelBottomLeft.getBlue() + mask[2][1] * pixelBottom.getBlue() + mask[2][2] * pixelBottomRight.getBlue();
				
				red = red / nbPixels;
				green = green / nbPixels;
				blue = blue / nbPixels;
				
				Pixel newPixel = new Pixel(red, green, blue);
				newData[i][j] = newPixel.getRGB();
			}
		}
		
		this.imageTransformed = new RGBImage(newData);
	}

	@Override
	public void apply(GreyImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}

	@Override
	public void apply(MonoImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}
	
	
	private int[][] filter(Image image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				int value = mask[0][0] * image.get(i-1, j-1) + mask[0][1] * image.get(i, j-1) + mask[0][2] * image.get(i+1, j-1) + 
							mask[1][0] * image.get(i-1, j)   + mask[1][1] * image.get(i, j)   + mask[1][2] * image.get(i+1, j) +
							mask[2][0] * image.get(i-1, j+1) + mask[2][1] * image.get(i, j+1) + mask[2][2] * image.get(i+1, j+1);
				
				int nbPixels = 9;
				
				if((i == 1 || i == image.getWidth() - 2) && (j == 1 || j == image.getHeight() - 2) ) nbPixels = 11;
				else if((i == 1 || i == image.getWidth() - 2) || (j == 1 || j == image.getHeight() - 2)) nbPixels = 13;
				else nbPixels = 16;
				
				value = value / nbPixels;
				newData[i][j] = Math.max(0, Math.min(255,  value));
			}
		}
		
		return newData;
	}
	
}
