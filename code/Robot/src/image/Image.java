package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import transform.VisitorImage;


/**
 * @author Maxime PINEAU
 * 
 */
/**
 * @author Maxime
 *
 */
public abstract class Image {
	
	protected int height;
	protected int width;
	protected int[][] matrix;
	
	public Image(int width, int heigth, int[][] data) {
		this.height = heigth;
		this.width = width;
		this.matrix = data;
	}
	
	public Image(int[][] data) {
		this(data.length, data[0].length, data);
	}
	
	public Image(int width, int height) {
		this(width, height, new int[width][height]);
	}
	
	public abstract void accept(VisitorImage visitorImage);
	
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
	
	public int[][] getCloneMatrix() {
		int[][] clone = new int[this.width][this.height];
		
		for(int i = 0; i < this.width; i++) {
			clone[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	
	/** 
	 * Return true if the coordinates i and j are in the image. 
	 * @param i the horizontal coordinate i  
	 * @param j the vertical coordinate j 
	 * @return 
	 */
	public boolean isInBound(int i, int j) {
		return i >= 0 && i < this.width && j >= 0 && j < this.height;
	}
	
	public int get(int i, int j) {
		return this.matrix[i][j];
	}
	
	public void set(int i, int j, int pixelValue) {
		this.matrix[i][j] = pixelValue;
	}
	
	
	/**
	 * Return the Moore Neiborhoods of a pixel given is position (i, j).
	 * The original pixel is also included in the result. 
	 * The Moore Neiborhoods correspond to the 8-connexes neibors.
	 * @param range the range of the neiborhoods
	 * @param i the coordinate i of the origin pixel 
	 * @param j the coordinate j of the origin pixel 
	 * @return a set of the 8-connexes neibors. 
	 * @see image.Image.getVonNeumannNeighborhoods 
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
	 * Return the Von Neumann Neiborhoods of a pixel at the position (i, j). 
	 * The origin pixel is also included into the result. 
	 * The Von Neumann Neiborhoods correspond to the 4-connexes neibords.
	 * @param range the range of the neiborhoods
	 * @param i the coordinate i of the origin pixel
	 * @param j the coordinate j of the origin pixel
	 * @return a set of the 4-connexe neibors 
	 * @see image.Image.getMooreNeighborhoods
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
	 * Save the image into a jpg file given by his path in a string.
	 * @param pathToFile 
	 * @throws IOException
	 */
	public void saveAs(String pathToFile) throws IOException {	
		this.saveAs(new File(pathToFile));
	}
	
	/**
	 * Save the image into a jpg file 
	 * @param file
	 * @throws IOException
	 */
	public void saveAs(File file) throws IOException {
		BufferedImage bufferedImage = this.getBufferedImage();
		ImageIO.write(bufferedImage, "jpg", file);
	}
	
		
}
