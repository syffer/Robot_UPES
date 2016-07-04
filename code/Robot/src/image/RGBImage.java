package image;

import java.awt.image.BufferedImage;

import transform.VisitorImage;

public class RGBImage extends Image {

	protected RGBImage(int width, int heigth) {
		super(width, heigth);
	}

	public RGBImage(int[][] data) {
		super(data);
	}
	
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
