package transform.filter;

import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

/**
 * Applies a weighted average filter to an image. 
 * 
 * Applies a convolution to each pixels given a certain filter, and then divide it 
 * by the number of surrounding pixels. 
 * 
 * @author Maxime PINEAU
 */
public class WeightedAverageFilter extends AbstractFilter {

	/**
	 * default mask : 
	 * 1 1 1 
	 * 1 8 1
	 * 1 1 1 
	 */
	public static final int[][] originalMask = {{1, 1, 1}, 
										 		 {1, 8, 1}, 
										 		 {1, 1, 1}};
	
	private final int[][] mask;
	
	public WeightedAverageFilter(int[][] mask) {
		this.mask = mask;
	}
	
	/**
	 * Creates a weighted average filter with the default mask 
	 */
	public WeightedAverageFilter() {
		this(WeightedAverageFilter.originalMask);
	}
	
	
	
	@Override
	public void visit(RGBImage image) {
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				

				int nbPixels = 9;
				
				if((i == 1 || i == image.getWidth() - 2) && (j == 1 || j == image.getHeight() - 2) ) nbPixels = 11;
				else if((i == 1 || i == image.getWidth() - 2) || (j == 1 || j == image.getHeight() - 2)) nbPixels = 13;
				else nbPixels = 16;
				
				// get the surrounding pixels 
				Pixel pixelTopLeft = image.getPixel(i-1,  j-1);
				Pixel pixelTop = image.getPixel(i,  j-1);
				Pixel pixelTopRight = image.getPixel(i+1,  j-1);
				
				Pixel pixelLeft = image.getPixel(i-1,  j);
				Pixel pixelMiddle = image.getPixel(i,  j);
				Pixel pixelRight = image.getPixel(i+1,  j);
				
				Pixel pixelBottomLeft = image.getPixel(i-1,  j+1);
				Pixel pixelBottom = image.getPixel(i,  j+1);
				Pixel pixelBottomRight = image.getPixel(i+1,  j+1);
				
				// convolution 
				int red = mask[0][0] * pixelTopLeft.getRed() + mask[0][1] * pixelTop.getRed() + mask[0][2] * pixelTopRight.getRed() + 
						  mask[1][0] * pixelLeft.getRed()   + mask[1][1] * pixelMiddle.getRed()   + mask[1][2] * pixelRight.getRed() +
						  mask[2][0] * pixelBottomLeft.getRed() + mask[2][1] * pixelBottom.getRed() + mask[2][2] * pixelBottomRight.getRed();
				
				int green = mask[0][0] * pixelTopLeft.getGreen() + mask[0][1] * pixelTop.getGreen() + mask[0][2] * pixelTopRight.getGreen() + 
							mask[1][0] * pixelLeft.getGreen()   + mask[1][1] * pixelMiddle.getGreen()   + mask[1][2] * pixelRight.getGreen() +
							mask[2][0] * pixelBottomLeft.getGreen() + mask[2][1] * pixelBottom.getGreen() + mask[2][2] * pixelBottomRight.getGreen();
				
				int blue = mask[0][0] * pixelTopLeft.getBlue() + mask[0][1] * pixelTop.getBlue() + mask[0][2] * pixelTopRight.getBlue() + 
						   mask[1][0] * pixelLeft.getBlue()   + mask[1][1] * pixelMiddle.getBlue()   + mask[1][2] * pixelRight.getBlue() +
						   mask[2][0] * pixelBottomLeft.getBlue() + mask[2][1] * pixelBottom.getBlue() + mask[2][2] * pixelBottomRight.getBlue();
				
				// applie the average 
				red = red / nbPixels;
				green = green / nbPixels;
				blue = blue / nbPixels;
				
				Pixel newPixel = new Pixel(red, green, blue);
				newData[i][j] = newPixel.getRGB();
			}
		}
		
		this.setTransformedImage(new RGBImage(newData));
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(new GreyImage(this.filter(image)));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(new GreyImage(this.filter(image)));
	}
	
	
	private int[][] filter(GreyImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				// convolution 
				int value = mask[0][0] * image.get(i-1, j-1) + mask[0][1] * image.get(i, j-1) + mask[0][2] * image.get(i+1, j-1) + 
							mask[1][0] * image.get(i-1, j)   + mask[1][1] * image.get(i, j)   + mask[1][2] * image.get(i+1, j) +
							mask[2][0] * image.get(i-1, j+1) + mask[2][1] * image.get(i, j+1) + mask[2][2] * image.get(i+1, j+1);
				
				int nbPixels = 9;
				
				if((i == 1 || i == image.getWidth() - 2) && (j == 1 || j == image.getHeight() - 2) ) nbPixels = 11;
				else if((i == 1 || i == image.getWidth() - 2) || (j == 1 || j == image.getHeight() - 2)) nbPixels = 13;
				else nbPixels = 16;
				
				// average 
				value = value / nbPixels;
				newData[i][j] = Math.max(0, Math.min(255,  value));
			}
		}
		
		return newData;
	}
	
}
