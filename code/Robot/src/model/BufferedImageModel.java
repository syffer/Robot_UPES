package model;

import java.awt.image.BufferedImage;

public class BufferedImageModel extends ImageModel {
	
	public BufferedImage bufferedImage;
	
	public BufferedImageModel(BufferedImage bufferedImage) {
		super(bufferedImage.getWidth(), bufferedImage.getHeight());
		this.bufferedImage = bufferedImage;
	}
		
	@Override
	public BufferedImage getBufferedImage() { 		
		return this.bufferedImage;
	}

	@Override
	public int get(int i, int j) {
		return this.bufferedImage.getRGB(i, j);
	}
	
}
