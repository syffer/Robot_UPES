package gui.controller;

import gui.model.FeatureExtractionModel;
import gui.view.FeatureExtractionView;

public class FeatureExtractionController extends InternalController {

	protected FeatureExtractionController(FeatureExtractionModel model) {
		super(model, new FeatureExtractionView(model));
	}

}
