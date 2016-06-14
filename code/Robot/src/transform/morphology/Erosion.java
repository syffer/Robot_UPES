package transform.morphology;

import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

public class Erosion extends Morphology {
		
	@Override
	public void apply(RGBImage image) {
		this.imageTransformed = new GreyImage(this.erode(image));		
	}

	@Override
	public void apply(GreyImage image) {
		this.imageTransformed = new GreyImage(this.erode(image));
	}

	@Override
	public void apply(MonoImage image) {
		this.imageTransformed = new GreyImage(this.erode(image));
	}
	

	private int[][] erode(Image image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			newData[i][0] = image.get(i, 0);
			newData[i][image.getHeight() - 1] = image.get(i, image.getHeight() - 1);
		}
		
		for(int j = 0; j < image.getHeight(); j++) {
			newData[0][j] = image.get(0, j);
			newData[image.getHeight() - 1][j] = image.get(image.getHeight() - 1, j);
		}
		
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				int minimum = image.get(i, j); 
								
				for(int k = 0; k < this.structuringElement.length; k++) {
					for(int l = 0; l < this.structuringElement[k].length; l++) {
						
						if(this.structuringElement[k][l] == 1) {
							int midX = (this.structuringElement.length - 1) / 2;
							int midY = (this.structuringElement[k].length - 1) / 2;
							minimum = Math.min(image.get(i + k - midX, j + l - midY), minimum);
						}
						
					}
				}
								
				//minimum = Math.max(0, minimum);
				newData[i][j] = minimum;
			}
		}
		
		return newData;
	}
	
}
