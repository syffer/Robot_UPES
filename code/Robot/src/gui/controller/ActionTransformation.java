package gui.controller;

import gui.model.ImageModel;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import transform.Transformation;

public class ActionTransformation extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;
	
	private GeneralController controller;
	
	private String name;
	private Transformation transformation;
	
	public ActionTransformation(GeneralController controller, String name, Transformation transformation) {
		super(name);
		
		this.controller = controller;
		this.controller.model.addObserver(this);
		
		this.name = name;
		this.transformation = transformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
		Image image = imageModel.getImage();
					
		long startTime = System.currentTimeMillis();
		image.accept(this.transformation);
		long endTime = System.currentTimeMillis();
		
		Image transformedImage = this.transformation.getTransformedImage();
		this.controller.addInternalModel(new ImageModel(transformedImage, image, this.name, endTime - startTime));
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}
