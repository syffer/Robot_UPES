package mvc.controller;

import mvc.model.ImageModel;
import mvc.view.ImageView;

public class ImageController extends InternalController {
	
	public ImageController(ImageModel imageModel) {
		super(imageModel, new ImageView(imageModel));
	}
	
}
