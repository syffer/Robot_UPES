package mvc.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import mvc.model.FeatureExtractionModel; 
import mvc.model.ImageModel;
import features.Feature;
import features.FeatureExtractor;

public class ActionFeatureExtraction extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionFeatureExtraction(GeneralController controller) {
		super("Feature Extraction");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
		MonoImage image = (MonoImage) imageModel.getImage();
		
		long startTime = System.currentTimeMillis();
		List<Feature> features = FeatureExtractor.extract(image);
		long endTime = System.currentTimeMillis();
		
		FeatureExtractionModel featureExtractionModel = new FeatureExtractionModel(image, features, endTime - startTime);		
		this.controller.addInternalModel(featureExtractionModel); 
	}

	@Override
	public void update(Observable observable, Object params) { 
		this.setEnabled(this.controller.hasImageModelSelected() && ((ImageModel) this.controller.getSelectedInternalModel()).getImage() instanceof MonoImage);
	} 
}