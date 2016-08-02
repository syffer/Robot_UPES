package image;

public abstract class AbstractGreyImage extends Image {

	public AbstractGreyImage(int width, int height) {
		super(width, height);
	}
	
	public abstract int get(int i, int j);
	public abstract void set(int i, int j, int grey);
	
	
	@Override
	public AbstractGreyImage clone() {
		return (AbstractGreyImage) super.clone();
	}
	
}
