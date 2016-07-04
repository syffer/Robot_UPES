package gui.model;

import image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageModel extends InternalModel {
	
	protected Image image;
	protected Image original;
	protected String operationName;
	protected double executionTime;
	
	public ImageModel(Image image, String operationName, double executionTime) {
		this(image, null, operationName, executionTime);
	}
	
	public ImageModel(Image image, Image original, String operationName, double executionTime) {
		this.image = image;
		this.original = original;
		this.operationName = operationName;
		this.executionTime = executionTime;
	}
		
	public BufferedImage getBufferedImage() {
		return this.image.getBufferedImage();
	}
	
	public String getOperationName() {
		return operationName;
	}
	
	public double getExecutionTime() {
		return this.executionTime / 1000;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public boolean hasOriginalImage() {
		return this.original != null;
	}
	
	public Image getOriginalImage() {
		return this.original;
	}
	
	public void saveImageAs(String pathToFile) throws IOException {	
		this.image.saveAs(pathToFile);
	}
	
	public void saveImageAs(File file) throws IOException {
		this.image.saveAs(file);
	}
		
	
}
