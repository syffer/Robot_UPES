package old;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.BufferedImageModel;
import model.ImageModel;

public class GreyScale {
	
	public static ImageModel transform(ImageModel imageModel) {
		BufferedImage image = GreyScale.transform(imageModel.getBufferedImage());
		return new BufferedImageModel(image);
	}
	
	public static BufferedImage transform(BufferedImage bufferedImage) {
		// see http://stackoverflow.com/questions/15972490/bufferedimage-getting-the-value-of-a-pixel-in-grayscale-color-model-picture 
		
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		/*
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int greyValue = bufferedImage.getRGB(i, j) & 0xFF;
				newImage.setRGB(i, j, greyValue);
			}
		}
		*/
		
	    Graphics g = newImage.getGraphics();
	    g.drawImage(bufferedImage, 0, 0, null);
	    g.dispose();
		
		return newImage;
	}
}