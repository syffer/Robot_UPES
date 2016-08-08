package transform.morphology;

import image.AbstractGreyImage;
import image.GreyImage;
import image.MonoImage;
import image.RGBImage;
import image.SegmentedImageGrey;
import image.SegmentedImageMono;
import image.SegmentedImageRGB;
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
		this.setTransformedImage(new GreyImage(image.getMatrix()));
	}

	


	@Override
	public void visit(SegmentedImageRGB image) { 
		SegmentedImageGrey segmentedImage = new SegmentedImageGrey(image.getWidth(), image.getHeight(), image.getBlockSize());
		this.visit(image, segmentedImage);
		this.setTransformedImage(segmentedImage);
	}

	@Override
	public void visit(SegmentedImageGrey image) {
		this.setTransformedImage(image);
	}

	@Override
	public void visit(SegmentedImageMono image) {
		SegmentedImageGrey segmentedImage = new SegmentedImageGrey(image.getWidth(), image.getHeight(), image.getBlockSize());
		this.visit(image, segmentedImage);
		this.setTransformedImage(segmentedImage);
	}
	
	
	@Override
	public AbstractGreyImage getTransformedImage() {
		return (AbstractGreyImage) super.getTransformedImage();
	}
	
}
