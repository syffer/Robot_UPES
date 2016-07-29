package transform.morphology;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;
import transform.Transformation;

public class GreyScale extends Transformation {

	@Override
	public void visit(RGBImage image) {
		this.setTransformedImage(new GreyImage(image));
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(image);
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(image);
	}

}
