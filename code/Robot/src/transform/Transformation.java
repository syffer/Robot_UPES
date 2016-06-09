package transform;

import model.GreyImageModel;
import model.ImageModel;
import model.MonoImageModel;
import model.RGBImageModel;

public abstract class Transformation {

	protected ImageModel imageTransformed;
	
	public abstract void apply(RGBImageModel image);
	public abstract void apply(GreyImageModel image);
	public abstract void apply(MonoImageModel image);
	
	public ImageModel getTransformedImage() {
		return this.imageTransformed;
	}
	
}
