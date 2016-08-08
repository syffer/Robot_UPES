package image;

import geometry.Position;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;



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
 * @author Maxime PINEAU
 * @see image.VisitorImage 
 */
public abstract class Image implements Cloneable {
	
	protected int height;
	protected int width;
	
	/**
	 * Creates an empty image (i.e all black).
	 * @param width the width of the image
	 * @param height the height of the image 
	 */
	public Image(int width, int height) {
		this.height = height;
		this.width = width;
	}
	
	/**
	 * @param visitorImage the visitor containing the operation to perform 
	 */
	public abstract void accept(VisitorImage visitorImage);
	
	
	public abstract Image getSubImage(int iStart, int jStart, int width, int height);
	
	/**
	 * @return the corresponding buffered image  
	 */
	public BufferedImage getBufferedImage() {
		BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				Pixel pixel = this.getPixel(i, j);
				bufferedImage.setRGB(i, j, pixel.getRGB());
			}
		}
		
		return bufferedImage;
	}
	

	
	public BufferedImage getBufferedImageToShow() {
		return this.getBufferedImage();
	}

	
	/**
	 * Returns the pixel value of the image at a certain position (i, j).
	 * @param i the i coordinate (width)
	 * @param j the j coordinate (height)
	 * @return the pixel value at the position (i, j) 
	 */
	public int getRGB(int i, int j) {
		return this.getPixel(i, j).getRGB();
	}
	
	public abstract Pixel getPixel(int i, int j);
	
	
	/**
	 * Sets the pixel value at the position (i, j) of the image. 
	 * @param i the i coordinate (width)
	 * @param j the j coordinate (height) 
	 * @param pixelValue the new pixel value of the position (i, j) 
	 */
	public void setRGB(int i, int j, int pixelValue) {
		this.set(i, j, new Pixel(pixelValue));
	}
	
	public abstract void set(int i, int j, Pixel pixel);
		
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
	 * @throws CloneNotSupportedException 
	 */
	public Image clone() {
		Image clone;
		try {
			clone = (Image) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError("clonnage impossible");
		}
		
	}
	/*
	public int[][] getCloneMatrix() {
		int[][] clone = new int[this.width][this.height];
		
		for(int i = 0; i < this.width; i++) {
			clone[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	*/
	
	/*
	@Override
	public Image clone() throws CloneNotSupportedException {
		Image clone = (Image) super.clone();
		return clone;
    }
    */
	
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
	 * Returns true if the coordinates i and j stocked in the position are in the image, false otherwise. 
	 * @param position the position containing the coordinate (i, j) 
	 * @return true if the position is in the image, false otherwise 
	 */
	public boolean isInBound(Position position) {
		return this.isInBound(position.i, position.j);
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
