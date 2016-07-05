package gui.model;

import java.util.Observable;


/**
 * Represents the general model used for the GUI. 
 * Memorizes the actual selected internal model to be able to communicate with the user. 
 * 
 * @author Maxime PINEAU
 */
public class GeneralModel extends Observable {

	private InternalModel selectedModel;
	
	public GeneralModel() {
		this.selectedModel = null;
	}
	
	public void initialise() { 
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * @return the selected internal model 
	 */
	public InternalModel getSelectedModel() {
		return this.selectedModel;
	}
	
	/**
	 * Sets the selected internal model to the given internal model
	 * @param selectedModel the new selected internal model 
	 */
	public void setSelectedModel(InternalModel selectedModel) {
		this.selectedModel = selectedModel;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * @return true if an internal model i sselected by the user, false otherwise 
	 */
	public boolean hasModelSelected() {
		return this.selectedModel != null;
	}
	
	/**
	 * @return true if the selected internal model is an image model, false otherwise 
	 */
	public boolean hasImageModelSelected() {
		return this.selectedModel instanceof ImageModel;
	}
	
}
