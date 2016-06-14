package transform;

import model.image.GreyImageModel;
import model.image.ImageModel;
import model.image.MonoImageModel;
import model.image.RGBImageModel;

public abstract class Transformation {

	protected ImageModel imageTransformed;
	
	public abstract void apply(RGBImageModel image);
	public abstract void apply(GreyImageModel image);
	public abstract void apply(MonoImageModel image);
	
	public ImageModel getTransformedImage() {
		return this.imageTransformed;
	}
	
}
