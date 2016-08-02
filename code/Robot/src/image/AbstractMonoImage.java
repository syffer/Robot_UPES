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
	
	@Override
	public AbstractMonoImage clone() {
		return (AbstractMonoImage) super.clone();
	}
	
}
