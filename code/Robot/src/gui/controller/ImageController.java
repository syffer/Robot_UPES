package gui.controller;

import gui.model.ImageModel;
import gui.view.ImageView;

public class ImageController extends InternalController {
	
	public ImageController(ImageModel imageModel) {
		super(imageModel, new ImageView(imageModel));
	}
	
}
