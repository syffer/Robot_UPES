package old;

import java.awt.image.BufferedImage;

import model.BufferedImageModel;
import model.ImageModel;

public class Monochromatic {
	
	public static ImageModel transform(ImageModel imageModel, int threshold) {
		BufferedImage image = Monochromatic.transform(imageModel.getBufferedImage(), threshold);
		return new BufferedImageModel(image);
	}
	
	public static BufferedImage transform(BufferedImage bufferedImage, int threshold) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int pixel = bufferedImage.getRGB(i, j);
				long p2 = bufferedImage.getRGB(i, j);
				
				
				System.out.println(pixel + " " + p2);
				
				if(pixel >= threshold) newImage.setRGB(i, j, 255);
				else newImage.setRGB(i, j, 0);
			}
		}
		
		return newImage;		
	}
	
}