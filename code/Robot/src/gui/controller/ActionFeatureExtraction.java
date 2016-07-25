package gui.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import features.FeatureExtractor;
import features.PositionnedObject;
import gui.model.FeatureExtractionModel;
import gui.model.ImageModel;

/**
 * Action that allows the user to perform a feature extraction on a monochromatic image. 
 * @see image.MonoImage 
 * @see features.Feature 
 * @see features.FeatureExtractor 
 * @author Maxime PINEAU
 */
public class ActionFeatureExtraction extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionFeatureExtraction(GeneralController controller) {
		super("Feature Extraction (Chain Code)");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
		MonoImage image = (MonoImage) imageModel.getImage();
		
		long startTime = System.currentTimeMillis();
		List<PositionnedObject> someObjects = FeatureExtractor.extract(image);
		long endTime = System.currentTimeMillis();
		
		//System.out.println(features);
		for(PositionnedObject object : someObjects) {
			System.out.println(object);
		}
		System.out.println();
		
		FeatureExtractionModel featureExtractionModel = new FeatureExtractionModel(image, someObjects, endTime - startTime);		
		this.controller.addInternalModel(featureExtractionModel); 
	}

	@Override
	public void update(Observable observable, Object params) { 
		this.setEnabled(this.controller.hasImageModelSelected() && ((ImageModel) this.controller.getSelectedInternalModel()).getImage() instanceof MonoImage);
	} 
}