package gui.model;

import image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Represents an internal model that contains : 
 * <ul>
 * 	<li>the name of an operation performed on an image</li>
 * 	<li>the original image</li>
 * 	<li>the resulting image</li>
 * </ul>the time the operation has taken</li>
 * @author Maxime PINEAU
 */
public class ImageModel extends InternalModel {
	
	protected Image image;
	protected Image original;
	protected String operationName;
	protected double executionTime;
	
	/**
	 * Creates a new ImageModel that contains an image resulting from a certain operation
	 * that doesn't use any other image (e.g. loading an image)
	 * @param image the image 
	 * @param operationName the operation name 
	 * @param executionTime the execution time that the operation has taken (in milliseconds)
	 */
	public ImageModel(Image image, String operationName, double executionTime) {
		this(image, null, operationName, executionTime);
	}
	
	/**
	 * Creates a new ImageModel that contains an image resulting from an operation 
	 * applied on another image (the original image) 
	 * @param image the image 
	 * @param original the original image on which the operation has been applied 
	 * @param operationName the operation name 
	 * @param executionTime the execution time that the operation has taken (in milliseconds)
	 */
	public ImageModel(Image image, Image original, String operationName, double executionTime) {
		this.image = image;
		this.original = original;
		this.operationName = operationName;
		this.executionTime = executionTime;
	}
		
	/**
	 * Returns the buffered image
	 * @return the buffered image 
	 */
	public BufferedImage getBufferedImage() {
		return this.image.getBufferedImage();
	}
	
	/**
	 * Returns the name of the operation performed
	 * @return the name of the operation performed 
	 */
	public String getOperationName() {
		return operationName;
	}
	
	/**
	 * Returns the time the operation has taken in seconds
	 * @return the time the operation has taken in seconds
	 */
	public double getExecutionTime() {
		return this.executionTime / 1000;
	}
	
	/**
	 * Returns the resulting immage after the operation 
	 * @return the resulting image 
	 */
	public Image getImage() {
		return this.image;
	}
	
	/**
	 * Returns true if the resulting image is from an operation applied on another image, false otherwise (e.g. loading an image)
	 * @return true if the resulting image is from an operation applied on another image 
	 */
	public boolean hasOriginalImage() {
		return this.original != null;
	}
	
	/**
	 * Returns the original image. Null if the image was just loaded
	 * @return the original image 
	 */
	public Image getOriginalImage() {
		return this.original;
	}
	
	/**
	 * Save the transformed image into the file at the given path 
	 * @param pathToFile the path to the file where the image should be saved 
	 * @throws IOException if an I/O error occurs
	 */
	public void saveImageAs(String pathToFile) throws IOException {	
		this.image.saveAs(pathToFile);
	}
	
	/**
	 * Save the transformed image into the given file 
	 * @param file the file where the image should be saved 
	 * @throws IOException if an I/O error occurs
	 */
	public void saveImageAs(File file) throws IOException {
		this.image.saveAs(file);
	}
		
	
}
