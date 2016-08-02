package image;

import java.awt.Color;
import java.awt.image.BufferedImage;


/**
 * Represents a grey image. 
 * Pixels have values contained between 0 and 255 included. 
 * 
 * @author Maxime PINEAU
 * @see image.Image 
 */
public class GreyImage extends AbstractGreyImage {
	
	protected int[][] matrix;
	
	
	private GreyImage(int width, int height, int[][] matrix) {
		super(width, height);
		this.matrix = matrix;
	}
	
	public GreyImage(int[][] data) {
		this(data.length, data[0].length, data);
	}
	
	/**
	 * Creates an empty grey image (i.e all black) 
	 * @param width the width of the image 
	 * @param height the height of the image 
	 */
	public GreyImage(int width, int height) {
		this(width, height, new int[width][height]);
	}
		
	/**
	 * Creates a grey image using a buffered image 
	 * @param bufferedImage the buffered image used to create the grey image 
	 */
	public GreyImage(BufferedImage bufferedImage) {
		super(bufferedImage.getWidth(), bufferedImage.getHeight()); 	
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				
				Color color = new Color(bufferedImage.getRGB(i, j));
				int grey = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				
				this.matrix[i][j] = grey;
			}
		}
	}
	
	public GreyImage(GreyImage greyImage) {
		super(greyImage.getWidth(), greyImage.getHeight());
		this.matrix = greyImage.matrix.clone();
	}
	
	public GreyImage(RGBImage rgbImageModel) {
		this(rgbImageModel.getWidth(), rgbImageModel.getHeight());
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				Pixel pixel = rgbImageModel.getPixel(i, j);
				this.set(i, j, pixel);
			}
		}
	}
	
	
	@Override
	public Pixel getPixel(int i, int j) {
		int grey = this.get(i, j);
		return new Pixel(grey, grey, grey);
	}
	
	@Override
	public int get(int i, int j) {
		return this.matrix[i][j];
	}
	

	@Override
	public void set(int i, int j, Pixel pixel) {
		this.matrix[i][j] = pixel.getGrey();
	}
	
	@Override
	public void set(int i, int j, int greyValue) {
		this.matrix[i][j] = greyValue;
	}

	@Override
	public GreyImage clone() {
		GreyImage clone = (GreyImage) super.clone();
		
		clone.matrix = new int[this.getWidth()][];
		for(int i = 0; i < this.getWidth(); i++) {
			clone.matrix[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	
	public int[][] getMatrix() {
		return this.matrix;
	}
	

	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.visit(this);
	}

	@Override
	public GreyImage getSubImage(int iStart, int jStart, int width, int height) {
		// exceptions !
		
		int[][] data = new int[width][height];
		
		for(int i = 0; i < width; i++) {
			System.arraycopy(this.matrix[i + iStart], jStart, data[i], 0, height);
		}
				
		return new GreyImage(data);
	}
	

	
	
}
