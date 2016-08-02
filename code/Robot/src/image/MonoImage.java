package image;

import java.awt.Color;
import java.awt.image.BufferedImage;


/**
 * Represents a monochromatic image (i.e. an image which contains only 2 values, back and white)
 * There is no verifications of any sort for the moment. 
 * 
 * The white must be represented by 255, and the black by 0. 
 * 
 * @author Maxime PINEAU
 * @see Image 
 */
public class MonoImage extends AbstractMonoImage {
	
	protected int[][] matrix;
	
	
	private MonoImage(int width, int height, int threshold, int[][] data) {
		super(width, height, threshold);
		this.matrix = data;
	}
	
	/**
	 * Creates a monochromatic image based on the given data.
	 * The data must have only 2 possible values : 
	 * - black : 0 
	 * - white : 255
	 * @param data the matrix used to create the monochromatic image. The only posible values have to be 0 and 255 (no verification). 
	 */
	public MonoImage(int[][] data, int threshold) {
		this(data.length, data[0].length, threshold, data);
	}
	
	public MonoImage(int width, int height, int threshold) {
		this(width, height, threshold, new int[width][height]);
	}
	
	/**
	 * Creates a monochromatic image from a grey image by using the threshold value.
	 * Pixels which have a value strictly under the given threshold will be represented in black, 
	 * the others will be represented in white. 
	 * @param greyImage the grey image. 
	 * @param threshold the threshold value, a pixel will be represented in black if its value is strictly under the threshold, and in white otherwise. 
	 */
	public MonoImage(GreyImage greyImage, int threshold) {
		this(greyImage.getWidth(), greyImage.getHeight(), threshold);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = greyImage.get(i, j);				
				int mono = (grey >= this.threshold) ? 255 : 0;
				this.set(i, j, mono);
			}
		}
	}
	
	
	/**
	 * Creates a monochromatic image from a buffered image and given a threshold
	 * @param bufferedImage the buffered image used to create the monochromatic image 
	 * @param threshold the threshold value, a pixel will be represented in black if its value is strictly under the threshold, and in white otherwise.   
	 * @see MonoImage#MonoImage(GreyImage, int)
	 */
	public MonoImage(BufferedImage bufferedImage, int threshold) {
		this(bufferedImage.getWidth(), bufferedImage.getHeight(), threshold);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				
				Color color = new Color(bufferedImage.getRGB(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				
				int mono = (grey >= this.threshold) ? 255 : 0;
				this.set(i, j, mono);
			}
		}
	}
	
	@Override
	public BufferedImage getBufferedImage() {	
		BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				int grey = this.matrix[i][j];// * 255;
				Color c = new Color(grey, grey, grey);
				bufferedImage.setRGB(i, j, c.getRGB()); 
			}
		}
		
		return bufferedImage;
	}
	
	
	
	@Override
	public int get(int i, int j) {
		return this.matrix[i][j];
	}

	@Override
	public void set(int i, int j, int grey) {
		this.matrix[i][j] = grey;
	}

	@Override
	public Pixel getPixel(int i, int j) {
		int grey = this.get(i, j);
		return new Pixel(grey, grey, grey);
	}

	@Override
	public void set(int i, int j, Pixel pixel) {
		this.set(i, j, pixel.getGrey());
	}
	
	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.visit(this);
	}
	
	public int[][] getMatrix() {
		return this.matrix;
	}
	
	@Override 
	public MonoImage clone() { 
		MonoImage clone = (MonoImage) super.clone();
		
		clone.matrix = new int[clone.getWidth()][];
		for(int i = 0; i < clone.getWidth(); i++) {
			clone.matrix[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	
	@Override
	public MonoImage getSubImage(int iStart, int jStart, int width, int height) {

		int[][] data = new int[width][height];
		
		for(int i = 0; i < width; i++) {
			System.arraycopy(this.matrix[i + iStart], jStart, data[i], 0, height);
		}
				
		
		return new MonoImage(data, this.threshold);
	}

	
	
}
