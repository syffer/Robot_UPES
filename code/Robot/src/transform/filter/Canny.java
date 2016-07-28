package transform.filter;

import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

/**
 * Applies a canny filter to an image. 
 * 
 * Canny is an edge detection operator. 
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Canny_edge_detector">https://en.wikipedia.org/wiki/Canny_edge_detector</a>
 * @author Maxime PINEAU
 */
public class Canny extends AbstractFilter {

	/**
	 * horizontal mask 
	 */
	public static final int[] maskX = {-1,  0,  1};
	
	
	/**
	 * vertical mask 
	 */
	public static final int[] maskY = { 1,  0, -1};
	
	
	@Override
	public void apply(RGBImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				Pixel pixelTop = image.getPixel(i,  j-1);
				Pixel pixelBottom = image.getPixel(i,  j+1);
				
				Pixel pixelMiddle = image.getPixel(i,  j);
				
				Pixel pixelLeft = image.getPixel(i-1,  j);
				Pixel pixelRigth = image.getPixel(i+1,  j);
				
				// applies masks for the red channel 
				int gradiantX = maskX[0] * pixelTop.getRed() + maskX[1] * pixelMiddle.getRed() + maskX[2] * pixelBottom.getRed();
				int gradiantY = maskY[0] * pixelLeft.getRed() + maskY[1] * pixelMiddle.getRed() + maskY[2] * pixelRigth.getRed();
				int red = Math.abs(gradiantX) + Math.abs(gradiantY);
				
				// applies masks for the green channel 
				gradiantX = maskX[0] * pixelTop.getGreen() + maskX[1] * pixelMiddle.getGreen() + maskX[2] * pixelBottom.getGreen();
				gradiantY = maskY[0] * pixelLeft.getGreen() + maskY[1] * pixelMiddle.getGreen() + maskY[2] * pixelRigth.getGreen();
				int green = Math.abs(gradiantX) + Math.abs(gradiantY);
				
				// applies the masks for the blue channel  
				gradiantX = maskX[0] * pixelTop.getBlue() + maskX[1] * pixelMiddle.getBlue() + maskX[2] * pixelBottom.getBlue();
				gradiantY = maskY[0] * pixelLeft.getBlue() + maskY[1] * pixelMiddle.getBlue() + maskY[2] * pixelRigth.getBlue();
				int blue = Math.abs(gradiantX) + Math.abs(gradiantY);
				
				// set the filtered value 
				Pixel pixel = new Pixel(red, green, blue);
				newData[i][j] = pixel.getRGB();
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
		this.imageTransformed = new MonoImage(this.filter(image), image.getThresholdValue());
	}
	
	
	private int[][] filter(GreyImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				int gradiantX = maskX[0] * image.get(i, j-1) + maskX[1] * image.get(i, j) + maskX[2] * image.get(i, j+1);
				
				int gradiantY = maskY[0] * image.get(i-1, j) + maskY[1] * image.get(i, j) + maskY[2] * image.get(i+1, j);
				
				int value = Math.abs(gradiantX) + Math.abs(gradiantY);
				newData[i][j] = Math.min(255, Math.max(0, value));
			}
		}
		
		return newData;
	}


	

}
