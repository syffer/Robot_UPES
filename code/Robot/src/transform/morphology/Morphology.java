package transform.morphology;

import model.GreyImageModel;
import model.ImageModel;

public class Morphology {

	// http://cdn.intechopen.com/pdfs-wm/11306.pdf 
	
	public static ImageModel erode(ImageModel image) {
		
		int[][] structuringElement = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				int minimum = image.get(i, j); 
								
				for(int k = 0; k < structuringElement.length; k++) {
					for(int l = 0; l < structuringElement[k].length; l++) {
						
						if(structuringElement[k][l] == 1) {
							int value = image.get(i + k, j + l);
							if(minimum > value) minimum = value;
						}
						
					}
				}
								
				//minimum = Math.max(0, minimum);
				newData[i][j] = minimum;
			}
		}
		
		return new GreyImageModel(newData);
	}
	
}
