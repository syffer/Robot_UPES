package gui.controller;

import gui.model.ImageModel;
import gui.view.ChoiceCanceledException;
import gui.view.JOptionPaneSlider;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import transform.symbolic.Clap;

/**
 * Action that allows the user to perform a CLAP algorithm on an image 
 * @see transform.symbolic.Clap
 * @author Maxime PINEAU
 */
public class ActionClap extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionClap(GeneralController controller) {
		super("CLAP");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		try {
			ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
			Image image = imageModel.getImage();
			
			int threshold = JOptionPaneSlider.showConfirmDialog(this.controller.view, "CLAP", 0, 255);
			Clap clap = new Clap(threshold);
			
			long startTime = System.currentTimeMillis();
			image.accept(clap);
			long endTime = System.currentTimeMillis();
			
			Image imageFiltered = clap.getTransformedImage();
			this.controller.addInternalModel(new ImageModel(imageFiltered, image, imageModel.getOriginalImage(), "CLAP (" + threshold + ")", endTime - startTime));	
			
		} catch (ChoiceCanceledException e) {
			
		}		
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}
