package transform.filter;

import model.GreyImageModel;
import model.ImageModel;

public class Laplacian extends AbstractFilter {

	private static final int[][] mask1 = {{ 0, -1,  0}, 
										  {-1,  4, -1}, 
										  { 0, -1,  0}};
	
	private static final int[][] mask2 = {{-1, -1, -1}, 
										  {-1,  8, -1}, 
										  {-1, -1, -1}};
	
	@Override
	public ImageModel filter(ImageModel image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				int value = mask2[0][0] * image.get(i-1, j-1) + mask2[0][1] * image.get(i, j-1) + mask2[0][2] * image.get(i+1, j-1) + 
							mask2[1][0] * image.get(i-1, j)   + mask2[1][1] * image.get(i, j)   + mask2[1][2] * image.get(i+1, j) +
							mask2[2][0] * image.get(i-1, j+1) + mask2[2][1] * image.get(i, j+1) + mask2[2][2] * image.get(i+1, j+1);
				
				newData[i][j] = Math.min(255, Math.max(0, value));
				
			}
		}
		
		return new GreyImageModel(newData);
	}

}
