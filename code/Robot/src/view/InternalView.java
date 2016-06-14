package view;

import javax.swing.JInternalFrame;

import model.InternalModel;

public abstract class InternalView extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	protected InternalModel model;
	
	
	
	
	
	
	public InternalModel getModel() {
		return this.model;
	}
}
