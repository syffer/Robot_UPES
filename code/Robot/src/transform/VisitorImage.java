package transform;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;

public interface VisitorImage {
	public void apply(RGBImage image);
	public void apply(GreyImage image);
	public void apply(MonoImage image);
}
