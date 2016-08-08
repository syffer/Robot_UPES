package dimention3D;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import image.AbstractGreyImage;
import image.GreyImage;
import image.Image;
import image.RGBImage;

public class DepthEstimation {
	
	private double focalLength;
	private double baseLine;
	
	public DepthEstimation(double focalLenght, double baseLine) throws DepthEstimationException { 
		if(focalLenght <= 0) throw new DepthEstimationException("'focal length' parameter has to be geater than 0.");
		if(baseLine <= 0) throw new DepthEstimationException("'base line' parameter has to be geater than 0.");
		
		this.focalLength = focalLenght;
		this.baseLine = baseLine;
	}
	
	public GreyImage estimate(Image left, Image right) throws DepthEstimationException {
		if(left.getWidth() != right.getWidth() || left.getHeight() != right.getHeight()) throw new DepthEstimationException("images don't have the same sizes");
		
		int[][] data = new int[left.getWidth()][left.getHeight()];
		
		double fb = this.focalLength * this.baseLine;
		
		for(int i = 0; i < left.getWidth(); i++) {
			for(int j = 0; j < left.getHeight(); j++) {
				data[i][j] = (int) (fb / Math.abs(left.getPixel(i, j).getGrey() - right.getPixel(i, j).getGrey()));
			}
		}
		
		return new GreyImage(data);
	}
	
	
	public GreyImage estimate(AbstractGreyImage disparityMap) {
		int[][] data = new int[disparityMap.getWidth()][disparityMap.getHeight()];
		
		double fb = this.focalLength * this.baseLine;
		
		for(int i = 0; i < disparityMap.getWidth(); i++) {
			for(int j = 0; j < disparityMap.getHeight(); j++) {
				data[i][j] = (int) (fb / disparityMap.get(i, j));
			}
		}
		
		return new GreyImage(data);
	}
	
	
	public static class Test {
		public static void main(String[] agrs) {
			
			
			try {
				File fileLeft = new File("./../../../images/i_left.png");
				File fileRight = new File("./../../../images/i_right.png");
				
				RGBImage left = new RGBImage(ImageIO.read(fileLeft));
				RGBImage right = new RGBImage(ImageIO.read(fileRight));
								
				DepthEstimation depthEstimation = new DepthEstimation(10, 10);
				GreyImage depthMap = depthEstimation.estimate(left, right);
				
				depthMap.saveAs("./../../../images/i_depth_map.jpg");
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DepthEstimationException e) {
				e.printStackTrace();
			}
		}
	}
}
