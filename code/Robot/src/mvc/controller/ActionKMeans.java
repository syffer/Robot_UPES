package mvc.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import clustering.KMeans;
import clustering.KMeansException;
import clustering.NumberOfVariablesException;

import mvc.model.FeatureExtractionModel;
import features.Feature;

public class ActionKMeans extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionKMeans(GeneralController controller) {
		super("KMeans");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		FeatureExtractionModel featureExtractionModel = (FeatureExtractionModel) this.controller.getSelectedInternalModel();
		List<Feature> features = featureExtractionModel.getFeatures();
		MonoImage image = featureExtractionModel.getOriginalImage();

		KMeans kmeans = new KMeans();
		
		long startTime = System.currentTimeMillis();
		try {
			kmeans.clustering(true, null, 4);
		} catch (NumberOfVariablesException | KMeansException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		
		//FeatureExtractionModel featureExtractionModel = new FeatureExtractionModel(image, features, endTime - startTime);		
		//this.controller.addInternalModel(featureExtractionModel); 
	}

	@Override
	public void update(Observable observable, Object params) { 
		this.setEnabled(this.controller.hasInternalModelSelected() && this.controller.getSelectedInternalModel() instanceof FeatureExtractionModel);
	} 
}
