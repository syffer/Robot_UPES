package transform;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;
import image.SegmentedImage;

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
		this.setTransformedImage(new SegmentedImage(image, this.blockSize));
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(new SegmentedImage(image, this.blockSize));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(new SegmentedImage(image, this.blockSize));
	}
	
	
	/*
	@Override
	public Segmentation clone() {
		return (Segmentation) super.clone();
	}
	*/

}
