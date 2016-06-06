package model;

import java.util.Observable;

public class GeneralModel extends Observable {

	private ImageModel selectedImageModel;
	
	public GeneralModel() {
		this.selectedImageModel = null;
	}
	
	public void initialise() { 
		this.setChanged();
		this.notifyObservers();
	}
	
	public ImageModel getSelectedImageModel() {
		return this.selectedImageModel;
	}
	
	public void setSelectedImageModel(ImageModel selectedImageModel) {
		this.selectedImageModel = selectedImageModel;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean hasImageModelSelected() {
		return this.selectedImageModel != null;
	}
	
	
}