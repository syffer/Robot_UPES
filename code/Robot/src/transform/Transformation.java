package transform;

import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.RGBImage;

/**
 * Applies a transformation to an image (filter, threshold, ...). 
 * @author Maxime PINEAU
 * @see VisitorImage 
 */
public abstract class Transformation implements VisitorImage {

	protected Image imageTransformed;
	
	public abstract void apply(RGBImage image);
	public abstract void apply(GreyImage image);
	public abstract void apply(MonoImage image);
	
	/**
	 * Returns the transformed image (i.e. the obtained image after applying the transformation) 
	 * @return the transofmed image 
	 */
	public Image getTransformedImage() {
		return this.imageTransformed;
	}
	
}
