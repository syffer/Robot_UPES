package gui.view;

import gui.model.InternalModel;

import javax.swing.JInternalFrame;


public abstract class InternalView extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	protected InternalModel internalModel;
	
	
	public InternalView(InternalModel internalModel) {
		this.internalModel = internalModel;
	}
	
	
	
	public InternalModel getInternalModel() {
		return this.internalModel;
	}
	
}