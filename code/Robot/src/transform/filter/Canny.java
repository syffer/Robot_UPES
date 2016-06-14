package transform.filter;

import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

public class Canny extends AbstractFilter {

	private static final int[] maskX = {-1,  0,  1};
	private static final int[] maskY = { 1,  0, -1};
	
	
	@Override
	public void apply(RGBImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}


	@Override
	public void apply(GreyImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}


	@Override
	public void apply(MonoImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}
	
	
	private int[][] filter(Image image) {
		
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				int gradiantX = maskX[0] * image.get(i, j-1) + maskX[1] * image.get(i, j) + maskX[2] * image.get(i, j+1);
				
				int gradiantY = maskY[0] * image.get(i-1, j) + maskY[1] * image.get(i, j) + maskY[2] * image.get(i+1, j);
				
				int value = Math.abs(gradiantX) + Math.abs(gradiantY);
				newData[i][j] = Math.min(255, Math.max(0, value));
			}
		}
		
		return newData;
	}


	

}
