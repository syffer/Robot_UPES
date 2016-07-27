package gui.controller;

import gui.model.ImageModel;
import gui.view.ChoiceCanceledException;
import gui.view.JOptionPaneSlider;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import transform.morphology.Threshold;

/**
 * Action that allows the user to perform a threshold on an image. 
 * Open a window so that the user can give the threshold value. 
 * @see image.Image
 * @see transform.morphology.Threshold
 * @author Maxime PINEAU
 */
public class ActionThreshold extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionThreshold(GeneralController controller) {
		super("Threshold");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		try {
			int value = JOptionPaneSlider.showConfirmDialog(this.controller.view, "Threshold", 0, 255);
			Threshold threshold = new Threshold(value);
				
			ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
			Image image = imageModel.getImage();
			
			long startTime = System.currentTimeMillis();
			//MonoImage monoImage = new MonoImage(image, threshold);
			image.accept(threshold);
			long endTime = System.currentTimeMillis();
			
			Image monoImage = threshold.getTransformedImage();
			this.controller.addInternalModel(new ImageModel(monoImage, image, "Monochromatic (" + value + ")", endTime - startTime));
			
		} catch (ChoiceCanceledException e) {
			// don't do anything
		}
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}
