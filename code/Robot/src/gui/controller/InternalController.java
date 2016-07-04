package gui.controller;

import gui.model.InternalModel;
import gui.view.InternalView;

public abstract class InternalController {
	
	public InternalModel internalModel;
	public InternalView internalView;
	
	protected InternalController(InternalModel model, InternalView view) { 
		this.internalModel = model;
		this.internalView = view;
	}
	
}
