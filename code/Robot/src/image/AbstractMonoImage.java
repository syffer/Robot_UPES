package image;

public abstract class AbstractMonoImage extends AbstractGreyImage {

	protected int threshold;
	
	public AbstractMonoImage(int width, int height, int threshold) {
		super(width, height);
		this.threshold = threshold;
	}
	
	public int getThreshold() {
		return this.threshold;
	}

}
