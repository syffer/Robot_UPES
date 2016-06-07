package filter;

import model.GreyImageModel;
import model.ImageModel;

public class MediumFilter extends AbstractFilter {

	private static final int[][] mask = {{1, 1, 1}, 
										 {1, 8, 1}, 
										 {1, 1, 1}};
	
	@Override
	public ImageModel filter(ImageModel image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				int value = mask[0][0] * image.get(i-1, j-1) + mask[0][1] * image.get(i, j-1) + mask[0][2] * image.get(i+1, j-1) + 
							mask[1][0] * image.get(i-1, j)   + mask[1][1] * image.get(i, j)   + mask[1][2] * image.get(i+1, j) +
							mask[2][0] * image.get(i-1, j+1) + mask[2][1] * image.get(i, j+1) + mask[2][2] * image.get(i+1, j+1);
				
				int nbPixels;
				if((i == 1 || i == image.getWidth() - 2) && (j == 1 || j == image.getHeight() - 2) ) nbPixels = 4;
				else if((i == 1 || i == image.getWidth() - 2) || (j == 1 || j == image.getHeight() - 2)) nbPixels = 6;
				else nbPixels = 9;
								
				value = value / nbPixels;
				newData[i][j] = Math.max(255, Math.min(0,  value));
			}
		}
		
		return new GreyImageModel(newData);
	}

}
