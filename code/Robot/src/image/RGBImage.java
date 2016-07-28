package image;

import java.awt.image.BufferedImage;


/**
 * Represents a RGB image (Red Green Blue) 
 * 
 * @author Maxime PINEAU
 * @see image.Image 
 */
public class RGBImage extends Image {

	private int[][] matrix;
	
	private RGBImage(int width, int height, int[][] matrix) {
		super(width, height);
		this.matrix = matrix;
	}
	
	/**
	 * Creates an empty RGB image (i.e all black)
	 * @param width the width of the image 
	 * @param heigth the height of the image 
	 */
	public RGBImage(int width, int height) {
		this(width, height, new int[width][height]);
	}

	/**
	 * Creates a RGB image using a matrix containing the pixel values. 
	 * @param data the matrix containing the pixel values. Each value is an interger which contain the alpha, red, green and blue value
	 */
	public RGBImage(int[][] data) { 
		this(data.length, data[0].length, data);
	}
	
	/**
	 * Creates a RGB image from a monochromatic image 
	 * @param image the monochromatic image 
	 */
	public RGBImage(MonoImage image) {
		this(image.getWidth(), image.getHeight());
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				this.setRGB(i, j, image.getRGB(i, j));
			}
		}
		
	}
	
	/**
	 * Creates a RGB image using a buffered image. 
	 * @param bufferedImage the buffered image used to create the RGB image 
	 */
	public RGBImage(BufferedImage bufferedImage) {
		this(bufferedImage.getWidth(), bufferedImage.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				this.setRGB(i, j, bufferedImage.getRGB(i, j));
			}
		}
	}
	

	@Override
	public Pixel getPixel(int i, int j) {
		return new Pixel(this.matrix[i][j]);
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
		this.matrix[i][j] = pixel.getRGB();
	}
	
	

	@Override
	public void set(int i, int j, Pixel pixel) {
		this.matrix[i][j] = pixel.getRGB();
	}
	
	public void setGreen(int i, int j, int green) {
		Pixel pixel = this.getPixel(i, j);
		pixel.setGreen(green);
		this.matrix[i][j] = pixel.getRGB();
	}
	
	public void setBlue(int i, int j, int blue) {
		Pixel pixel = this.getPixel(i, j);
		pixel.setBlue(blue);
		this.matrix[i][j] = pixel.getRGB();
	}
	
	@Override
	public RGBImage clone() {
		
		try {
			RGBImage clone = (RGBImage) super.clone();
			
			clone.matrix = new int[this.getWidth()][];
			for(int i = 0; i < this.getWidth(); i++) {
				clone.matrix[i] = this.matrix[i].clone();
			}
			
			return clone;
		}
		catch(CloneNotSupportedException e) {
			throw new InternalError("clonage impossible");
		}
	}
	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.apply(this);
	}
	
}
