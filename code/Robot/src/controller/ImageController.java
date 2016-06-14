package controller;

import view.ViewImage;
import model.InternalModel;
import model.image.ImageModel;

public class ImageController {
	
	public InternalModel model;
	public ViewImage imageView;
	
	public ImageController(ImageModel imageModel, String operationName, double executionTime) {
		this(imageModel, new ViewImage(imageModel, operationName, executionTime / 1000.0));
	}
	
	public ImageController(InternalModel model, ViewImage imageView) { 
		this.model = model;
		this.imageView = imageView;
	}
	
}
