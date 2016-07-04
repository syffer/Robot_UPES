package image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import transform.VisitorImage;

/**
 * Represents a monochromatic image (i.e. an image which contains only 2 values, back and white)
 * There is no verifications of any sort for the moment. 
 * 
 * The white must be represented by 255, and the black by 0. 
 * 
 * @author Maxime PINEAU
 * @see Image 
 */
public class MonoImage extends GreyImage {
	
	/**
	 * Creates a monochromatic image based on the given data.
	 * The data must have only 2 possible values : 
	 * - black : 0 
	 * - white : 255
	 * @param data the matrix used to create the monochromatic image. The only posible values have to be 0 and 255 (no verification). 
	 */
	public MonoImage(int[][] data) {
		super(data);
	}
	
	
	/**
	 * Creates a monochromatic image from a grey image by using the threshold value.
	 * Pixels which have a value strictly under the given threshold will be represented in black, 
	 * the others will be represented in white. 
	 * @param greyImage the grey image. 
	 * @param threshold the threshold value, a pixel will be represented in black if its value is strictly under the threshold, and in white otherwise. 
	 */
	public MonoImage(GreyImage greyImage, int threshold) {
		super(greyImage.getWidth(), greyImage.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = greyImage.get(i, j);				
				this.matrix[i][j] = (grey >= threshold) ? 255 : 0;
			}
		}
	}
	
	public MonoImage(Image image, int threshold) {
		this(image.getBufferedImage(), threshold);
	}
	
	/**
	 * Creates a monochromatic image from a buffered image and given a threshold
	 * @param bufferedImage the buffered image used to create the monochromatic image 
	 * @param threshold the threshold value, a pixel will be represented in black if its value is strictly under the threshold, and in white otherwise.   
	 * @see MonoImage#MonoImage(GreyImage, int)
	 */
	public MonoImage(BufferedImage bufferedImage, int threshold) {
		super(bufferedImage.getWidth(), bufferedImage.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				
				Color color = new Color(bufferedImage.getRGB(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				
				this.matrix[i][j] = (grey >= threshold) ? 255 : 0;
			}
		}
	}
	
	@Override
	public BufferedImage getBufferedImage() {	
		BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = this.matrix[i][j];// * 255;
				Color c = new Color(grey, grey, grey);
				bufferedImage.setRGB(i, j, c.getRGB()); 
			}
		}
		
		return bufferedImage;
	}
	
	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.apply(this);
	}
	
}
