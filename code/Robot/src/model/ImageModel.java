package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.image.Image;

public class ImageModel extends InternalModel {

	private Image image;
	private String operationName;
	private double executionTime;
	
	public ImageModel(Image image, String operationName, double executionTime) {
		this.image = image;
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
		return executionTime;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	
	public void saveImageAs(String pathToFile) throws IOException {	
		this.image.saveAs(pathToFile);
	}
	
	public void saveImageAs(File file) throws IOException {
		this.image.saveAs(file);
	}
		
	
}
