package gui.controller;

import gui.model.ImageModel;
import gui.view.ChoiceCanceledException;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;


public class ActionSaveAs extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionSaveAs(GeneralController controller) {
		super("Save as ...");
		
		this.controller = controller;
		controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		try {
			File file = this.controller.view.getFileToSave();
			
			ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
			imageModel.saveImageAs(file);
			
		} catch (ChoiceCanceledException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
	
}
