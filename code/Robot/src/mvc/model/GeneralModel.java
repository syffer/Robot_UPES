package mvc.model;

import java.util.Observable;


public class GeneralModel extends Observable {

	private InternalModel selectedModel;
	
	public GeneralModel() {
		this.selectedModel = null;
	}
	
	public void initialise() { 
		this.setChanged();
		this.notifyObservers();
	}
	
	public InternalModel getSelectedModel() {
		return this.selectedModel;
	}
	
	public void setSelectedModel(InternalModel selectedModel) {
		this.selectedModel = selectedModel;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean hasModelSelected() {
		return this.selectedModel != null;
	}
	
	public boolean hasImageModelSelected() {
		return this.selectedModel instanceof ImageModel;
	}
	
}
