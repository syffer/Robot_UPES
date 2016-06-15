package controller;

import view.InternalView;
import model.InternalModel;

public abstract class InternalController {
	
	public InternalModel internalModel;
	public InternalView internalView;
	
	protected InternalController(InternalModel model, InternalView view) { 
		this.internalModel = model;
		this.internalView = view;
	}
	
}
