package controller;

import view.ImageView;
import model.ImageModel;

public class ImageController {
	
	public ImageModel imageModel;
	public ImageView imageView;
	
	public ImageController(ImageModel imageModel, String operationName, double executionTime) {
		this(imageModel, new ImageView(imageModel, operationName, executionTime));
	}
	
	public ImageController(ImageModel imageModel, ImageView imageView) { 
		this.imageModel = imageModel;
		this.imageView = imageView;
	}
	
}
