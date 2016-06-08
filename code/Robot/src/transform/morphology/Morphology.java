package transform.morphology;

import model.GreyImageModel;
import model.ImageModel;

public class Morphology {

	// http://cdn.intechopen.com/pdfs-wm/11306.pdf 
	
	public static ImageModel erosion(ImageModel image) {
		
		int[][] structuringElement = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				int minimum = image.get(i, j); 
				
				for(int k = 0; k < structuringElement.length; k++) {
					for(int l = 0; l < structuringElement[k].length; l++) {
						
						if(minimum - structuringElement[k][l]*255 < minimum) minimum = minimum - structuringElement[k][l]*255;
						
					}
				}
				
				System.out.println(minimum + " " + image.get(i, j));
				
				minimum = Math.max(0, minimum);
				newData[i][j] = minimum;
			}
		}
		
		return new GreyImageModel(newData);
	}
	
}
