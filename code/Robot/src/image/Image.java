package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import transform.VisitorImage;


/** 
 * Base class of an image, allowing to stock the pixel values.
 * Images can be represented as : 
 * - Red Green Blue (RGB) 
 * - gray 
 * - monochromatic  
 * 
 * A visitor pattern is used in order to perform different process 
 * according to the instance of the image (RGB, grey, ...).
 * 
 * @author Maxime 
 * @see transform.VisitorImage 
 */
public abstract class Image {
	
	protected int height;
	protected int width;
	protected int[][] matrix;
	
	protected Image(int width, int heigth, int[][] data) {
		this.height = heigth;
		this.width = width;
		this.matrix = data;
	}
	
	
	/**
	 * Creates an image using the data provided.
	 * The data will contain the pixel values as integer.
	 * @param data the matrix containing the pixel values
	 */
	public Image(int[][] data) {
		this(data.length, data[0].length, data);
	}
	
	/**
	 * Creates an empty image (i.e all black).
	 * @param width the width of the image
	 * @param height the height of the image 
	 */
	public Image(int width, int height) {
		this(width, height, new int[width][height]);
	}
	
	/**
	 * @param visitorImage the visitor containing the operation to perform 
	 */
	public abstract void accept(VisitorImage visitorImage);
	
	/**
	 * @return the corresponding buffered image  
	 */
	public abstract BufferedImage getBufferedImage();
			
	

	/**
	 * @return the height of the image
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width of the image 
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the number of pixels in the image 
	 */
	public int getNbPixels() {
		return this.width * this.height;
	}
	
	
	/**
	 * @return a clone of the matrix containing the pixel values of the image. 
	 */
	public int[][] getCloneMatrix() {
		int[][] clone = new int[this.width][this.height];
		
		for(int i = 0; i < this.width; i++) {
			clone[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	
	/** 
	 * Returns true if the coordinates i and j are in the image, false otherwise. 
	 * @param i the horizontal coordinate i (width)
	 * @param j the vertical coordinate j (height) 
	 * @return true if the coordinates are in the image, false otherwise
	 */
	public boolean isInBound(int i, int j) {
		return i >= 0 && i < this.width && j >= 0 && j < this.height;
	}
	
	
	/**
	 * Returns the pixel value of the image at a certain position (i, j).
	 * @param i the i coordinate (width)
	 * @param j the j coordinate (height)
	 * @return the pixel value at the position (i, j) 
	 */
	public int get(int i, int j) {
		return this.matrix[i][j];
	}
	
	/**
	 * Sets the pixel value at the position (i, j) of the image. 
	 * @param i the i coordinate (width)
	 * @param j the j coordinate (height) 
	 * @param pixelValue the new pixel value of the position (i, j) 
	 */
	public void set(int i, int j, int pixelValue) {
		this.matrix[i][j] = pixelValue;
	}
	
	
	/**
	 * Returns the Moore Neiborhoods of a pixel given is position (i, j).
	 * The original pixel is also included in the result. 
	 * The Moore Neiborhoods correspond to the 8-connexes neibors.
	 * @param range the range of the neiborhoods
	 * @param i the coordinate i of the origin pixel (width)
	 * @param j the coordinate j of the origin pixel (height) 
	 * @return a set of the 8-connexes neibors. 
	 * @see Image#getVonNeumannNeighborhoods 
	 */
	public Set<Position> getMooreNeighborhoods(int range, int i, int j) {
		// 8 connexe 
		
		Set<Position> neighborsIndex = new HashSet<Position>();
		
		for(int k = i - range; k <= i + range; k++) {
			for(int l = j - range; l <= j + range; l++) {
				if(this.isInBound(k, l)) neighborsIndex.add(new Position(k, l));
			}
		}
		
		return neighborsIndex;
	}
	
	/**
	 * Returns the Von Neumann Neiborhoods of a pixel at the position (i, j). 
	 * The origin pixel is also included into the result. 
	 * The Von Neumann Neiborhoods correspond to the 4-connexes neibords.
	 * @param range the range of the neiborhoods
	 * @param i the coordinate i of the origin pixel (width) 
	 * @param j the coordinate j of the origin pixel (height) 
	 * @return a set of the 4-connexe neibors 
	 * @see Image#getMooreNeighborhoods
	 */
	public Set<Position> getVonNeumannNeighborhoods(int range, int i, int j) {
		// 4 connexe 
		
		Set<Position> neighborsIndex = new HashSet<Position>();
		
		for(int k = i - range; k <= i + range; k++) {
			for(int l = j - range; l <= j + range; l++) {
				// select only the top, left, bottom and right neibors 
				if(this.isInBound(k, l) && Math.abs(k - i) + Math.abs(l - j) <= range) {
					neighborsIndex.add(new Position(k, l));
				}
			}
		}
		
		return neighborsIndex;
	}
	
	
	
	/**
	 * Saves the image into a jpg file given by his path in a string.
	 * @param pathToFile the path to the file 
	 * @throws IOException If an I/O error occurs (e.g. the file is already in use)
	 */
	public void saveAs(String pathToFile) throws IOException {	
		this.saveAs(new File(pathToFile));
	}
	
	/**
	 * Saves the image into a jpg file 
	 * @param file the file 
	 * @throws IOException If an I/O error occurs
	 */
	public void saveAs(File file) throws IOException {
		BufferedImage bufferedImage = this.getBufferedImage();
		ImageIO.write(bufferedImage, "jpg", file);
	}
	
		
}
