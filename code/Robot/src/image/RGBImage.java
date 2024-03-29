package image;

import java.awt.image.BufferedImage;


/**
 * Represents a RGB image (Red Green Blue) 
 * 
 * @author Maxime PINEAU
 * @see image.Image 
 */
public class RGBImage extends AbstractRGBImage {

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
	public RGBImage(Image image) {
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

	@Override
	public void set(int i, int j, Pixel pixel) {
		this.matrix[i][j] = pixel.getRGB();
	}
		
	@Override
	public RGBImage clone() {
		RGBImage clone = (RGBImage) super.clone();
		
		clone.matrix = new int[this.getWidth()][];
		for(int i = 0; i < this.getWidth(); i++) {
			clone.matrix[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.visit(this);
	}

	@Override
	public RGBImage getSubImage(int iStart, int jStart, int width, int height) {
		// exceptions !
		
		int[][] data = new int[width][height];
		
		for(int i = 0; i < width; i++) {
			System.arraycopy(this.matrix[i + iStart], jStart, data[i], 0, height);
		}
		return new RGBImage(data);
		
	}
}
