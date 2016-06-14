package controller;

import view.ImageView;
import view.InternalView;
import model.ImageModel;
import model.InternalModel;

public class InternalController {
	
	public InternalModel internalModel;
	public InternalView internalView;
	
	public InternalController(ImageModel imageModel) {
		this(imageModel, new ImageView(imageModel));
	}
	
	
	private InternalController(InternalModel model, InternalView view) { 
		this.internalModel = model;
		this.internalView = view;
	}
	
}
