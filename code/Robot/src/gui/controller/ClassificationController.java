package gui.controller;

import gui.model.ClassificationModel;
import gui.view.ClassificationView;

/**
 * Controller of the classification internal model and view. 
 * Creates the classification internal view.
 * @see image.MonoImage
 * @see features.Feature
 * @see clustering.KMeans
 * @author Maxime PINEAU
 */
public class ClassificationController extends InternalController {

	protected ClassificationController(ClassificationModel model) {
		super(model, new ClassificationView(model));
	}

}