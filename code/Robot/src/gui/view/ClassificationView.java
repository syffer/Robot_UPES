package gui.view;

import gui.model.ClassificationModel;


/**
 * Represents an internal view that contains the results of the classification 
 * of the feature extraction performed on an image. 
 * @author Maxime PINEAU
 */
public class ClassificationView extends ImageView {
	private static final long serialVersionUID = 1L;
	
	public ClassificationView(ClassificationModel classificationModel) {
		super(classificationModel);
	}
	
}