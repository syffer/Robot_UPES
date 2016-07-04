package image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import transform.VisitorImage;

/**
 * Represents a grey image 
 * 
 * @author Maxime
 * @see image.Image 
 */
public class GreyImage extends Image {
	
	public GreyImage(int[][] data) {
		super(data);
	}
	
	/**
	 * Creates an empty grey image (i.e all black) 
	 * @param width
	 * @param height
	 */
	public GreyImage(int width, int height) {
		super(width, height);
	}
	
	public GreyImage(Image image) {
		this(image.getBufferedImage());
	}
	
	public GreyImage(BufferedImage bufferedImage) {
		super(bufferedImage.getWidth(), bufferedImage.getHeight()); 	
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				
				Color color = new Color(bufferedImage.getRGB(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				
				this.matrix[i][j] = grey;
			}
		}
	}
	
	public GreyImage(GreyImage greyImage) {
		super(greyImage.getWidth(), greyImage.getHeight());
		this.matrix = greyImage.matrix.clone();
	}
	
	public GreyImage(RGBImage rgbImageModel) {
		super(rgbImageModel.getWidth(), rgbImageModel.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				
				Color color = new Color(rgbImageModel.get(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				
				this.matrix[i][j] = grey;
			}
		}
	}
	
	@Override
	public BufferedImage getBufferedImage() {
		
		BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = this.matrix[i][j];
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
