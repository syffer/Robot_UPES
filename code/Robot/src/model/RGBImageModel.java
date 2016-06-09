package model;

import java.awt.image.BufferedImage;

import transform.Transformation;

public class RGBImageModel extends ImageModel {

	protected RGBImageModel(int width, int heigth) {
		super(width, heigth);
	}

	public RGBImageModel(int[][] data) {
		super(data);
	}
	
	public RGBImageModel(BufferedImage bufferedImage) {
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
	public void accept(Transformation transformation) {
		transformation.apply(this);
	}
	
}
