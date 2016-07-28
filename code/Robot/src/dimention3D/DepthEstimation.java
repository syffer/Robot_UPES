package dimention3D;

import image.GreyImage;

public class DepthEstimation {
	
	private double focalLength;
	private double baseLine;
	
	public DepthEstimation(double focalLenght, double baseLine) {
		this.focalLength = focalLenght;
		this.baseLine = baseLine;
	}
	
	public GreyImage estimate(GreyImage left, GreyImage right) throws Exception {
		if(left.getWidth() != right.getWidth() || left.getHeight() != right.getHeight()) throw new Exception("grey images don't have the same sizes");
		
		int[][] data = new int[left.getWidth()][left.getHeight()];
		
		double fb = this.focalLength * this.baseLine;
		
		for(int i = 0; i < left.getWidth(); i++) {
			for(int j = 0; j < left.getHeight(); j++) {
				data[i][j] = (int) (fb / Math.abs(left.get(i, j) - right.get(i, j)));
			}
		}
		
		return new GreyImage(data);
	}
	
	
	public GreyImage estimate(GreyImage disparityMap) {
		int[][] data = new int[disparityMap.getWidth()][disparityMap.getHeight()];
		
		double fb = this.focalLength * this.baseLine;
		
		for(int i = 0; i < disparityMap.getWidth(); i++) {
			for(int j = 0; j < disparityMap.getHeight(); j++) {
				data[i][j] = (int) (fb / disparityMap.get(i, j));
			}
		}
		
		return new GreyImage(data);
	}
}
