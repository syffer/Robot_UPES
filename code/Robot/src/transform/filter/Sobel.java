package transform.filter;

import model.GreyImageModel;
import model.ImageModel;

public class Sobel extends AbstractFilter {

	// https://blog.saush.com/2011/04/20/edge-detection-with-the-sobel-operator-in-ruby/ 
	// http://stackoverflow.com/questions/19331515/implenting-a-sobel-filter-in-imagej 
	
	private static final int[][] maskX = {{-1, 0, 1}, 
										  {-2, 0, 2}, 
										  {-1, 0, 1}};
	
	private static final int[][] maskY = {{-1, -2, -1}, 
										  { 0,  0,  0}, 
										  { 1,  2,  1}};
	
	public ImageModel filter(ImageModel image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		// the result is a image with less pixels 
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				// convolution 2D 
				int newX = 	maskX[0][0] * image.get(i-1, j-1) + maskX[0][1] * image.get(i, j-1) + maskX[0][2] * image.get(i+1, j-1) + 
							maskX[1][0] * image.get(i-1, j)   + maskX[1][1] * image.get(i, j)   + maskX[1][2] * image.get(i+1, j) +
							maskX[2][0] * image.get(i-1, j+1) + maskX[2][1] * image.get(i, j+1) + maskX[2][2] * image.get(i+1, j+1);
				
				int newY = 	maskY[0][0] * image.get(i-1, j-1) + maskY[0][1] * image.get(i, j-1) + maskY[0][2] * image.get(i+1, j-1) + 
							maskY[1][0] * image.get(i-1, j)   + maskY[1][1] * image.get(i, j)   + maskY[1][2] * image.get(i+1, j) +
							maskY[2][0] * image.get(i-1, j+1) + maskY[2][1] * image.get(i, j+1) + maskY[2][2] * image.get(i+1, j+1);
				
				// gradiant
				newData[i][j] = (int) Math.ceil(Math.sqrt((newX * newX) + (newY * newY))); 
				newData[i][j] = Math.min(255, Math.max(0, newData[i][j]));
			}
		}
				
		return new GreyImageModel(newData);		
	}
}
