package transform.filter;

import model.GreyImageModel;
import model.ImageModel;

public class Canny extends AbstractFilter {

	private static final int[] maskX = {-1,  0,  1};
	private static final int[] maskY = { 1,  0, -1};
	
	
	@Override
	public ImageModel filter(ImageModel image) {
		
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				/*
				int newV = 	mask2[0][0] * image.get(i-1, j-1) + mask2[0][1] * image.get(i, j-1) + mask2[0][2] * image.get(i+1, j-1) + 
							mask2[1][0] * image.get(i-1, j)   + mask2[1][1] * image.get(i, j)   + mask2[1][2] * image.get(i+1, j) +
							mask2[2][0] * image.get(i-1, j+1) + mask2[2][1] * image.get(i, j+1) + mask2[2][2] * image.get(i+1, j+1);
				*/
				int gradiantX = maskX[0] * image.get(i, j-1) + maskX[1] * image.get(i, j) + maskX[2] * image.get(i, j+1);
				
				int gradiantY = maskY[0] * image.get(i-1, j) + maskY[1] * image.get(i, j) + maskY[2] * image.get(i+1, j);
				
				int value = Math.abs(gradiantX) + Math.abs(gradiantY);
				newData[i][j] = Math.min(255, Math.max(0, value));
			}
		}
		
		return new GreyImageModel(newData);
	}

}
