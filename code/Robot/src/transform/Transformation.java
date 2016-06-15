package transform;

import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

public abstract class Transformation implements VisitorImage {

	protected Image imageTransformed;
	
	public abstract void apply(RGBImage image);
	public abstract void apply(GreyImage image);
	public abstract void apply(MonoImage image);
	
	public Image getTransformedImage() {
		return this.imageTransformed;
	}
	
}
