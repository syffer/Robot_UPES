package transform;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;
import image.SegmentedImageGrey;
import image.SegmentedImageMono;
import image.SegmentedImageRGB;

public class Segmentation extends Transformation {

	private int blockSize;

	public Segmentation() {
		this(8);
	}
	
	public Segmentation(int blocSize) {
		this.blockSize = blocSize;
	}
	
	
	@Override
	public void visit(RGBImage image) {
		this.setTransformedImage(new SegmentedImageRGB(image, this.blockSize));
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(new SegmentedImageGrey(image, this.blockSize));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(new SegmentedImageMono(image, this.blockSize));
	}

	
	@Override
	public void visit(SegmentedImageRGB image) {
		this.setTransformedImage(image);
	}

	@Override
	public void visit(SegmentedImageGrey image) {
		this.setTransformedImage(image);
	}

	@Override
	public void visit(SegmentedImageMono image) {
		this.setTransformedImage(image);
	}
	
	
	/*
	@Override
	public Segmentation clone() {
		return (Segmentation) super.clone();
	}
	*/

}
