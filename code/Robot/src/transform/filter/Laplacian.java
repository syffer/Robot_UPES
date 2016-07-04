package transform.filter;

import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

/**
 * Applies a Laplacian filter on an image. 
 * Laplacian is an edge detector operation. 
 * 
 * @see <a href="http://www.tutorialspoint.com/dip/laplacian_operator.htm">http://www.tutorialspoint.com/dip/laplacian_operator.htm</a>
 * @author Maxime PINEAU
 */
public class Laplacian extends AbstractFilter {

	/**
	 * first possible mask : 
	 *  0 -1  0
	 * -1  4 -1
	 *  0 -1  0
	 */
	private static final int[][] mask1 = {{ 0, -1,  0}, 
										  {-1,  4, -1}, 
										  { 0, -1,  0}};
	
	/**
	 * second possible mask : 
	 * -1 -1 -1 
	 * -1  8 -1
	 * -1 -1 -1
	 */
	private static final int[][] mask2 = {{-1, -1, -1}, 
										  {-1,  8, -1}, 
										  {-1, -1, -1}};
	
	/**
	 * Type of the masks 
	 * @author Maxime PINEAU
	 */
	public enum Type {
		MASK_1,
		MASK_2,
	}
	
	
	private final int[][] mask;
	
	protected Laplacian(int[][] mask) {
		this.mask = mask;
	}
	
	/**
	 * Creates a Laplacian filter using the given mask 
	 * @param typeMask the type of the mask to use 
	 */
	public Laplacian(Type typeMask) {
		this((typeMask == Type.MASK_1) ? Laplacian.mask1 : Laplacian.mask2);
	}
	
	/**
	 * Creates a Laplacian filter using the second mask
	 * @see Laplacian#mask2 
	 */
	public Laplacian() {
		this(Laplacian.mask2);
	}
	
	
	@Override
	public void apply(RGBImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				// get the surrounding pixels 
				Pixel pixelTopLeft = new Pixel(image.get(i-1,  j-1));
				Pixel pixelTop = new Pixel(image.get(i,  j-1));
				Pixel pixelTopRight = new Pixel(image.get(i+1,  j-1));
				
				Pixel pixelLeft = new Pixel(image.get(i-1,  j));
				Pixel pixelMiddle = new Pixel(image.get(i,  j));
				Pixel pixelRight = new Pixel(image.get(i+1,  j));
				
				Pixel pixelBottomLeft = new Pixel(image.get(i-1,  j+1));
				Pixel pixelBottom = new Pixel(image.get(i,  j+1));
				Pixel pixelBottomRight = new Pixel(image.get(i+1,  j+1));
				
				// apply the convolution 
				
				// red channel 
				int red = mask[0][0] * pixelTopLeft.getRed() + mask[0][1] * pixelTop.getRed() + mask[0][2] * pixelTopRight.getRed() + 
						  mask[1][0] * pixelLeft.getRed()   + mask[1][1] * pixelMiddle.getRed()   + mask[1][2] * pixelRight.getRed() +
						  mask[2][0] * pixelBottomLeft.getRed() + mask[2][1] * pixelBottom.getRed() + mask[2][2] * pixelBottomRight.getRed();
				
				// green channel 
				int green = mask[0][0] * pixelTopLeft.getGreen() + mask[0][1] * pixelTop.getGreen() + mask[0][2] * pixelTopRight.getGreen() + 
							mask[1][0] * pixelLeft.getGreen()   + mask[1][1] * pixelMiddle.getGreen()   + mask[1][2] * pixelRight.getGreen() +
							mask[2][0] * pixelBottomLeft.getGreen() + mask[2][1] * pixelBottom.getGreen() + mask[2][2] * pixelBottomRight.getGreen();
				
				// blue channel 
				int blue = mask[0][0] * pixelTopLeft.getBlue() + mask[0][1] * pixelTop.getBlue() + mask[0][2] * pixelTopRight.getBlue() + 
						   mask[1][0] * pixelLeft.getBlue()   + mask[1][1] * pixelMiddle.getBlue()   + mask[1][2] * pixelRight.getBlue() +
						   mask[2][0] * pixelBottomLeft.getBlue() + mask[2][1] * pixelBottom.getBlue() + mask[2][2] * pixelBottomRight.getBlue();
				
				// set the new value 
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
				
				// apply the convolution 
				int value = mask[0][0] * image.get(i-1, j-1) + mask[0][1] * image.get(i, j-1) + mask[0][2] * image.get(i+1, j-1) + 
							mask[1][0] * image.get(i-1, j)   + mask[1][1] * image.get(i, j)   + mask[1][2] * image.get(i+1, j) +
							mask[2][0] * image.get(i-1, j+1) + mask[2][1] * image.get(i, j+1) + mask[2][2] * image.get(i+1, j+1);
				
				newData[i][j] = Math.min(255, Math.max(0, value));
			}
		}
		
		return newData;
	}
	
}
