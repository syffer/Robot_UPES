package gui.controller;

import gui.model.ImageModel;
import image.GreyImage;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;


/**
 * Action that allow the user to pass an image to a grey scale 
 * @see image.GreyImage
 * @author Maxime PINEAU
 */
public class ActionGreyScale extends AbstractAction implements Observer {
	private static final long serialVersionUID = 6509734674576991637L;

	private GeneralController controller;
	
	public ActionGreyScale(GeneralController controller) {
		super("Grey Scale"); 
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
		
		long startTime = System.currentTimeMillis();
		Image greyImage = new GreyImage(imageModel.getImage());
		long endTime = System.currentTimeMillis();
		
		this.controller.addInternalModel(new ImageModel(greyImage, imageModel.getOriginalImage(), "Grey Scale", endTime - startTime));
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}
