package transform;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;

public class Negative extends Transformation {

	@Override
	public void apply(RGBImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(GreyImage image) {		
		GreyImage grey = new GreyImage(image.getWidth(), image.getHeight());
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); i++) {
				
				int negative = 255 - image.get(i, j);
				grey.set(i, j, negative);
			}
		}
		
		this.imageTransformed = grey;
	}

	@Override
	public void apply(MonoImage image) {
		this.apply((GreyImage) image);
	}

}
