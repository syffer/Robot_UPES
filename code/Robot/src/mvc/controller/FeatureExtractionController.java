package mvc.controller;

import mvc.model.FeatureExtractionModel;
import mvc.view.FeatureExtractionView;

public class FeatureExtractionController extends InternalController {

	protected FeatureExtractionController(FeatureExtractionModel model) {
		super(model, new FeatureExtractionView(model));
	}

}
