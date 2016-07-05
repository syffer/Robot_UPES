package gui.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import clustering.Cluster;
import clustering.Individual;
import clustering.KMeans;
import clustering.KMeansException;
import clustering.NumberOfVariablesException;

import features.Feature;
import gui.model.ClassificationModel;
import gui.model.FeatureExtractionModel;
import gui.view.ChoiceCanceledException;
import gui.view.JOptionPaneSlider;

/**
 * Action that allows the user to perform a KMeans classification algorithm on the 
 * features extracted from a monochromatic image, and display the resulsts on the GUI. 
 * 
 * Asks to the user the number of classes that sould be used.
 * 
 * @see clustering.KMeans
 * @see features.Feature 
 * @author Maxime PINEAU
 */
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
		
		try {
			
			// creation on the individual from the features 
			Cluster cluster = new Cluster(10);
			for(Feature feature : features) {
				Individual individual = new Individual(feature.getArea(), 
														feature.getPerimeter(), 
														feature.getCompactness(), 
														feature.getCircularity(), 
														feature.getCurvature(), 
														feature.getBendingEnergy(), 
														feature.getWidth(), 
														feature.getHeight(), 
														feature.getRatioWidthHeight(), 
														feature.getDepth());
				cluster.add(individual);
			}
			
			int nbClasses = JOptionPaneSlider.showConfirmDialog(this.controller.view, "Classification - Number of Classes", 1, features.size());
			
			
			KMeans kmeans = new KMeans();
			
			long startTime = System.currentTimeMillis();
			kmeans.clustering(cluster, nbClasses);
			long endTime = System.currentTimeMillis();
			
			ClassificationModel classificationModel = new ClassificationModel(image, features, nbClasses, kmeans.getClasses(), endTime - startTime);
			this.controller.addInternalModel(classificationModel); 
			
			
		} catch (NumberOfVariablesException | KMeansException e) {
			e.printStackTrace();
		} catch (ChoiceCanceledException e) {
			// do nothing 
		}
		
		
		
	}

	@Override
	public void update(Observable observable, Object params) { 
		this.setEnabled(this.controller.hasInternalModelSelected() && this.controller.getSelectedInternalModel() instanceof FeatureExtractionModel);
	} 
}
