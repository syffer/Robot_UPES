package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MonoImageModel extends MatrixImageModel {
	
	public MonoImageModel(int[][] data) {
		super(data);
	}
	
	
	public MonoImageModel(GreyImageModel greyImageModel, int threshold) {
		super(greyImageModel.getWidth(), greyImageModel.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = greyImageModel.get(i, j);				
				this.matrix[i][j] = (grey >= threshold) ? 255 : 0;
			}
		}
	}
	
	public MonoImageModel(ImageModel imageModel, int threshold) {
		this(imageModel.getBufferedImage(), threshold);
	}
	
	public MonoImageModel(BufferedImage bufferedImage, int threshold) {
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
	
}
