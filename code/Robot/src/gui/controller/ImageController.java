package gui.controller;

import gui.model.ImageModel;
import gui.view.ImageView;

/**
 * Controller of the image model and view.
 * Creates the image view.
 * @see image.Image 
 * @author Maxime PINEAU
 */
public class ImageController extends InternalController {
	
	public ImageController(ImageModel imageModel) {
		super(imageModel, new ImageView(imageModel));
	}
	
}
