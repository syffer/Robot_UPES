package transform.morphology;

import image.AbstractGreyImage;
import image.GreyImage;
import image.MonoImage;
import image.RGBImage;

/**
 * Applies a dilatation to an image. 
 * 
 * @author Maxime PINEAU
 */
public class Dilation extends Morphology {
	
	@Override
	public void visit(RGBImage image) {
		
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(new GreyImage(this.dilate(image)));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(new MonoImage(this.dilate(image), image.getThreshold()));
	}
	

	private int[][] dilate(AbstractGreyImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			newData[i][0] = image.get(i, 0);
			newData[i][image.getHeight() - 1] = image.get(i, image.getHeight() - 1);
		}
		
		for(int j = 0; j < image.getHeight(); j++) {
			newData[0][j] = image.get(0, j);
			newData[image.getWidth() - 1][j] = image.get(image.getWidth() - 1, j);
		}
		
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				int maximum = image.get(i, j); 
								
				for(int k = 0; k < this.structuringElement.length; k++) {
					for(int l = 0; l < this.structuringElement[k].length; l++) {
						
						if(this.structuringElement[k][l] == 1) {
							int midX = (this.structuringElement.length - 1) / 2;
							int midY = (this.structuringElement[k].length - 1) / 2;
							maximum = Math.max(image.get(i + k - midX, j + l - midY), maximum);
						}
						
					}
				}
				
				newData[i][j] = maximum;
			}
		}
		
		return newData;
	}

}
