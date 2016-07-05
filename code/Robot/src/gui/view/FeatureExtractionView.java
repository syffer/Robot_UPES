package gui.view;

import gui.model.FeatureExtractionModel;


/**
 * Represents an internal view that contains the results of the feature extraction
 * @author Maxime PINEAU
 */
public class FeatureExtractionView extends ImageView {
	private static final long serialVersionUID = 1L;
	
	public FeatureExtractionView(FeatureExtractionModel featureExtractionModel) {
		super(featureExtractionModel);
	}
	
}
