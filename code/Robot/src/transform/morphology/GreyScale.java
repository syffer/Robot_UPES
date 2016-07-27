package transform.morphology;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;
import transform.Transformation;

public class GreyScale extends Transformation {

	@Override
	public void apply(RGBImage image) {
		this.imageTransformed = new GreyImage(image);
	}

	@Override
	public void apply(GreyImage image) {
		this.imageTransformed = image;
	}

	@Override
	public void apply(MonoImage image) {
		this.imageTransformed = (GreyImage) image;
	}

}
