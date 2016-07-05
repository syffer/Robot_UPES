package gui.controller;

import gui.model.InternalModel;
import gui.view.InternalView;

/**
 * Controller of the internal model and view.
 * Creates the internal view.
 * @author Maxime PINEAU
 */
public abstract class InternalController {
	
	public InternalModel internalModel;
	public InternalView internalView;
	
	protected InternalController(InternalModel model, InternalView view) { 
		this.internalModel = model;
		this.internalView = view;
	}
	
}
