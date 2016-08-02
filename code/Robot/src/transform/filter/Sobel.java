package transform.filter;

import image.AbstractGreyImage;
import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

/**
 * Applies a Sobel filer to an image. 
 * A sobel filter is an edge detection operator. 
 * 
 * @see <a href="https://blog.saush.com/2011/04/20/edge-detection-with-the-sobel-operator-in-ruby/">https://blog.saush.com/2011/04/20/edge-detection-with-the-sobel-operator-in-ruby/ </a>
 * @see <a href="http://stackoverflow.com/questions/19331515/implenting-a-sobel-filter-in-imagej">http://stackoverflow.com/questions/19331515/implenting-a-sobel-filter-in-imagej</a>
 * @author Maxime PINEAU
 */
public class Sobel extends AbstractFilter {
	
	private static final int[][] maskX = {{-1, 0, 1}, 
										  {-2, 0, 2}, 
										  {-1, 0, 1}};
	
	private static final int[][] maskY = {{-1, -2, -1}, 
										  { 0,  0,  0}, 
										  { 1,  2,  1}};
	
	
	@Override
	public void visit(RGBImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		// the result is a image with less pixels 
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
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
				
				// red
				int newX = 	maskX[0][0] * pixelTopLeft.getRed() + maskX[0][1] * pixelTop.getRed() + maskX[0][2] * pixelTopRight.getRed() + 
							maskX[1][0] * pixelLeft.getRed()   + maskX[1][1] * pixelMiddle.getRed()   + maskX[1][2] * pixelRight.getRed() +
							maskX[2][0] * pixelBottomLeft.getRed() + maskX[2][1] * pixelBottom.getRed() + maskX[2][2] * pixelBottomRight.getRed();
				
				int newY = 	maskY[0][0] * pixelTopLeft.getRed() + maskY[0][1] * pixelTop.getRed() + maskY[0][2] * pixelTopRight.getRed() + 
							maskY[1][0] * pixelLeft.getRed() + maskY[1][1] * pixelMiddle.getRed()   + maskY[1][2] * pixelRight.getRed() +
							maskY[2][0] * pixelBottomLeft.getRed() + maskY[2][1] * pixelBottom.getRed() + maskY[2][2] * pixelBottomRight.getRed();
				
				int red = (int) Math.ceil(Math.sqrt((newX * newX) + (newY * newY)));
				
				// green 
				newX = 	maskX[0][0] * pixelTopLeft.getGreen() + maskX[0][1] * pixelTop.getGreen() + maskX[0][2] * pixelTopRight.getGreen() + 
						maskX[1][0] * pixelLeft.getGreen()   + maskX[1][1] * pixelMiddle.getGreen()   + maskX[1][2] * pixelRight.getGreen() +
						maskX[2][0] * pixelBottomLeft.getGreen() + maskX[2][1] * pixelBottom.getGreen() + maskX[2][2] * pixelBottomRight.getGreen();
			
				newY = 	maskY[0][0] * pixelTopLeft.getGreen() + maskY[0][1] * pixelTop.getGreen() + maskY[0][2] * pixelTopRight.getGreen() + 
						maskY[1][0] * pixelLeft.getGreen() + maskY[1][1] * pixelMiddle.getGreen()   + maskY[1][2] * pixelRight.getGreen() +
						maskY[2][0] * pixelBottomLeft.getGreen() + maskY[2][1] * pixelBottom.getGreen() + maskY[2][2] * pixelBottomRight.getGreen();
				
				int green = (int) Math.ceil(Math.sqrt((newX * newX) + (newY * newY)));
				
				// blue 
				newX = 	maskX[0][0] * pixelTopLeft.getBlue() + maskX[0][1] * pixelTop.getBlue() + maskX[0][2] * pixelTopRight.getBlue() + 
						maskX[1][0] * pixelLeft.getBlue()   + maskX[1][1] * pixelMiddle.getBlue()   + maskX[1][2] * pixelRight.getBlue() +
						maskX[2][0] * pixelBottomLeft.getBlue() + maskX[2][1] * pixelBottom.getBlue() + maskX[2][2] * pixelBottomRight.getBlue();
			
				newY = 	maskY[0][0] * pixelTopLeft.getBlue() + maskY[0][1] * pixelTop.getBlue() + maskY[0][2] * pixelTopRight.getBlue() + 
						maskY[1][0] * pixelLeft.getBlue() + maskY[1][1] * pixelMiddle.getBlue()   + maskY[1][2] * pixelRight.getBlue() +
						maskY[2][0] * pixelBottomLeft.getBlue() + maskY[2][1] * pixelBottom.getBlue() + maskY[2][2] * pixelBottomRight.getBlue();
				
				int blue = (int) Math.ceil(Math.sqrt((newX * newX) + (newY * newY)));
				
				// calculate the gradiant
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
		this.setTransformedImage(new MonoImage(this.filter(image), image.getThreshold()));
	}
	
	
	private int[][] filter(AbstractGreyImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		// the result is a image with less pixels 
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				// convolution 2D 
				int newX = 	maskX[0][0] * image.get(i-1, j-1) + maskX[0][1] * image.get(i, j-1) + maskX[0][2] * image.get(i+1, j-1) + 
							maskX[1][0] * image.get(i-1, j)   + maskX[1][1] * image.get(i, j)   + maskX[1][2] * image.get(i+1, j) +
							maskX[2][0] * image.get(i-1, j+1) + maskX[2][1] * image.get(i, j+1) + maskX[2][2] * image.get(i+1, j+1);
				
				int newY = 	maskY[0][0] * image.get(i-1, j-1) + maskY[0][1] * image.get(i, j-1) + maskY[0][2] * image.get(i+1, j-1) + 
							maskY[1][0] * image.get(i-1, j)   + maskY[1][1] * image.get(i, j)   + maskY[1][2] * image.get(i+1, j) +
							maskY[2][0] * image.get(i-1, j+1) + maskY[2][1] * image.get(i, j+1) + maskY[2][2] * image.get(i+1, j+1);
				
				// gradiant
				newData[i][j] = (int) Math.ceil(Math.sqrt((newX * newX) + (newY * newY))); 
				newData[i][j] = Math.min(255, Math.max(0, newData[i][j]));
			}
		}
				
		return newData;		
	}

	
}
