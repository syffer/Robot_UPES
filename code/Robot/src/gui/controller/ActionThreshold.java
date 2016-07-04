package gui.controller;

import gui.model.ImageModel;
import gui.view.ChoiceCanceledException;
import gui.view.JOptionPaneSlider;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import transform.Threshold;

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
			int value = JOptionPaneSlider.showConfirmDialog(this.controller.view, "Monochromatic", 0, 255);
			Threshold threashold = new Threshold(value);
				
			ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
			Image image = imageModel.getImage();
			
			long startTime = System.currentTimeMillis();
			//MonoImage monoImage = new MonoImage(image, threshold);
			image.accept(threashold);
			long endTime = System.currentTimeMillis();
			
			Image monoImage = threashold.getTransformedImage();
			this.controller.addInternalModel(new ImageModel(monoImage, image, "Monochromatic", endTime - startTime));
			
		} catch (ChoiceCanceledException e) {
			// don't do anything
		}
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}
