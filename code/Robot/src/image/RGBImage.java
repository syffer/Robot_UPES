package image;

import java.awt.image.BufferedImage;

import transform.VisitorImage;

/**
 * Represents a RGB image (Red Green Blue) 
 * 
 * @author Maxime
 * @see image.Image 
 */
public class RGBImage extends Image {

	/**
	 * Creates an empty RGB image (i.e all black)
	 * @param width the width of the image 
	 * @param heigth the height of the image 
	 */
	protected RGBImage(int width, int heigth) {
		super(width, heigth);
	}

	/**
	 * Creates a RGB image using a matrix containing the pixel values. 
	 * @param data
	 */
	public RGBImage(int[][] data) {
		super(data);
	}
	
	/**
	 * Creates a RGB image from a monochromatic image 
	 * @param image the monochromatic image 
	 */
	public RGBImage(MonoImage image) {
		super(image.getWidth(), image.getHeight());
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int grey = image.get(i, j);
				Pixel pixel = new Pixel(grey, grey, grey);
				this.matrix[i][j] = pixel.getRGB();
			}
		}
		
	}
	
	/**
	 * Creates a RGB image using a buffered image. 
	 * @param bufferedImage
	 */
	public RGBImage(BufferedImage bufferedImage) {
		this(bufferedImage.getWidth(), bufferedImage.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				this.matrix[i][j] = bufferedImage.getRGB(i, j);
			}
		}
	}
	
	@Override
	public BufferedImage getBufferedImage() {
		BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				bufferedImage.setRGB(i, j, this.matrix[i][j]);
			}
		}
		
		return bufferedImage;
	}

	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.apply(this);
	}
	
}
