package image;

public abstract class AbstractRGBImage extends Image {
	
	public AbstractRGBImage(int width, int height) {
		super(width, height);
	}
	public int getRed(int i, int j) {
		return this.getPixel(i, j).getRed();
	}
	
	public int getGreen(int i, int j) {
		return this.getPixel(i, j).getGreen();
	}
	
	public int getBlue(int i, int j) {
		return this.getPixel(i, j).getBlue();
	}
	
	public void setRed(int i, int j, int red) {
		Pixel pixel = this.getPixel(i, j);
		pixel.setRed(red);
		this.set(i, j, pixel);
	}
	
	public void setGreen(int i, int j, int green) {
		Pixel pixel = this.getPixel(i, j);
		pixel.setGreen(green);
		this.set(i, j, pixel);
	}
	
	public void setBlue(int i, int j, int blue) {
		Pixel pixel = this.getPixel(i, j);
		pixel.setBlue(blue);
		this.set(i, j, pixel);
	}
}
