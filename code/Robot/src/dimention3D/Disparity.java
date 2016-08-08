package dimention3D;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import image.GreyImage;
import image.Image;
import image.RGBImage;

public class Disparity {

	public GreyImage estimate(Image left, Image right) throws DisparityException {
		if(left.getWidth() != right.getWidth() || left.getHeight() != right.getHeight()) throw new DisparityException("images don't have the same sizes");
		
		int[][] data = new int[left.getWidth()][left.getHeight()];
				
		for(int i = 0; i < left.getWidth(); i++) {
			for(int j = 0; j < left.getHeight(); j++) { 
				data[i][j] = (int) Math.abs(left.getPixel(i, j).getGrey() - right.getPixel(i, j).getGrey());
			}
		}
		
		return new GreyImage(data);
	}
	
	
	
	public static class DisparityException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public DisparityException(String message) {
			super(message);
		}
	}
	
	
	

	
	public static class Test {
		public static void main(String[] agrs) {
			
			
			try {
				File fileLeft = new File("./../../../images/i_left.png");
				File fileRight = new File("./../../../images/i_right.png");
				
				RGBImage left = new RGBImage(ImageIO.read(fileLeft));
				RGBImage right = new RGBImage(ImageIO.read(fileRight));
				
				Disparity disparity = new Disparity();
				GreyImage depthMap = disparity.estimate(left, right);
				
				depthMap.saveAs("./../../../images/i_disparity_result.jpg");
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DisparityException e) {
				e.printStackTrace();
			}
		}
	}
	
}
