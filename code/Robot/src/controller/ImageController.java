package controller;

import model.ImageModel;
import view.ImageView;

public class ImageController extends InternalController {
	
	public ImageController(ImageModel imageModel) {
		super(imageModel, new ImageView(imageModel));
	}
	
}
