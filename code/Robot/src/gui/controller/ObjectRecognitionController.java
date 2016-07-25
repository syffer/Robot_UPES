package gui.controller;

import gui.model.ObjectRecognitionModel;
import gui.view.ObjectRecognitionView;

public class ObjectRecognitionController extends InternalController {

	protected ObjectRecognitionController(ObjectRecognitionModel model) {
		super(model, new ObjectRecognitionView(model));
	}

}