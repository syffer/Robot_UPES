package gui.controller;

import gui.model.FeatureExtractionModel;
import gui.view.FeatureExtractionView;

/**
 * Controller of the feature extraction model and view.
 * Creates the feature extraction view.
 * @see image.MonoImage
 * @see features.Feature
 * @see features.FeatureExtractor
 * @author Maxime PINEAU
 */
public class FeatureExtractionController extends InternalController {

	protected FeatureExtractionController(FeatureExtractionModel model) {
		super(model, new FeatureExtractionView(model));
	}

}
