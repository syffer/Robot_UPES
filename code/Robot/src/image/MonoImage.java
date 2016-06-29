package image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import transform.VisitorImage;

public class MonoImage extends Image {
	
	public MonoImage(int[][] data) {
		super(data);
	}
	
	
	public MonoImage(GreyImage greyImageModel, int threshold) {
		super(greyImageModel.getWidth(), greyImageModel.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = greyImageModel.get(i, j);				
				this.matrix[i][j] = (grey >= threshold) ? 255 : 0;
			}
		}
	}
	
	public MonoImage(Image imageModel, int threshold) {
		this(imageModel.getBufferedImage(), threshold);
	}
	
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
