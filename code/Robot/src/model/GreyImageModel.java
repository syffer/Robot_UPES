package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GreyImageModel extends MatrixImageModel {
			
	public GreyImageModel(BufferedImage bufferedImage) {
		super(bufferedImage.getWidth(), bufferedImage.getHeight()); 	
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				
				Color color = new Color(bufferedImage.getRGB(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				
				this.matrix[i][j] = grey;
			}
		}
	}
	
	public GreyImageModel(RGBImageModel rgbImageModel) {
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
	
}