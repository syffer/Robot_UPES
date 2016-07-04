package gui.controller;

import gui.model.ClassificationModel;
import gui.view.ClassificationView;

public class ClassificationController extends InternalController {

	protected ClassificationController(ClassificationModel model) {
		super(model, new ClassificationView(model));
	}

}