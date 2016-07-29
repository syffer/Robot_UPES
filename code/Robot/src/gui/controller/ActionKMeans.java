package gui.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import classification.Cluster;
import classification.Individual;
import classification.KMeans;
import classification.KMeansException;
import classification.NumberOfVariablesException;

import features.Feature;
import features.PositionnedObject;
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
 * @see classification.KMeans
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
		List<PositionnedObject> someObjects = featureExtractionModel.getExtractedObjects();
		MonoImage image = featureExtractionModel.getPreviousImage();
		
		try {
			
			// creation on the individual from the features 
			Cluster cluster = new Cluster(9);
			for(PositionnedObject someObject : someObjects) {
				Feature feature = someObject.getFeature();
				Individual individual = new Individual(feature.getArea(), 
														feature.getPerimeter(), 
														feature.getCompactness(), 
														feature.getCircularity(), 
														feature.getCurvature(), 
														feature.getBendingEnergy(), 
														feature.getWidth(), 
														feature.getHeight(), 
														feature.getRatioWidthHeight());
				cluster.add(individual);
			}
			
			int nbClasses = JOptionPaneSlider.showConfirmDialog(this.controller.view, "Classification - Number of Classes", 1, someObjects.size());
			
			
			KMeans kmeans = new KMeans();
			
			long startTime = System.currentTimeMillis();
			kmeans.clustering(cluster, nbClasses);
			long endTime = System.currentTimeMillis();
			
			ClassificationModel classificationModel = new ClassificationModel(image, featureExtractionModel.getOriginalImage(), someObjects, nbClasses, kmeans.getClasses(), endTime - startTime);
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
