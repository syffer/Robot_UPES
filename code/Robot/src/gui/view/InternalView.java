package gui.view;

import gui.model.InternalModel;

import javax.swing.JInternalFrame;


/**
 * Represents a view/window in the desktop of the GUI
 * @author Maxime PINEAU
 */
public abstract class InternalView extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	protected InternalModel internalModel;
	
	
	/**
	 * Creates a new internal view 
	 * @param internalModel the associated internal model 
	 */
	public InternalView(InternalModel internalModel) {
		this.internalModel = internalModel;
	}
	
	
	
	/**
	 * Returns the internal model corresponding to the internal view
	 * @return the associated internal model 
	 */
	public InternalModel getInternalModel() {
		return this.internalModel;
	}
	
}
