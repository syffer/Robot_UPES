package transform;

import model.image.GreyImage;
import model.image.MonoImage;
import model.image.RGBImage;

public interface VisitorImage {
	public void apply(RGBImage image);
	public void apply(GreyImage image);
	public void apply(MonoImage image);
}
