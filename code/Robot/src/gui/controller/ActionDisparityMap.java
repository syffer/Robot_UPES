package gui.controller;

import gui.model.ImageModel;
import image.GreyImage;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import dimention3D.Disparity;
import dimention3D.Disparity.DisparityException;

public class ActionDisparityMap extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;
	
	private GeneralController controller;
	
	public ActionDisparityMap(GeneralController generalController) {
		super("Disparity map"); 
		
		this.controller = generalController;
		generalController.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		ImageModel model = (ImageModel) this.controller.getSelectedInternalModel();
		Image image = model.getImage();
		
		try {
			
			// transform image left to right (with a translation ? / sub image ?)
			//Image left = image.getSubImage(0, 0, image.getWidth() - 1, image.getHeight());
			//Image right = image.getSubImage(1, 0, image.getWidth() - 1, image.getHeight());
			
			int gap = 10; // pixel
			GreyImage left = new GreyImage(image.getWidth() + gap, image.getHeight());
			GreyImage right = new GreyImage(image.getWidth() + gap, image.getHeight());
			for(int i = 0; i < image.getWidth(); i++) {
				for(int j = 0; j < image.getHeight(); j++) {
					left.set(i + gap, j, image.getPixel(i, j).getGrey());
					right.set(i, j, image.getPixel(i, j).getGrey());
				}
			}
			
			long startTime = System.currentTimeMillis();
			Disparity disparity = new Disparity();
			GreyImage disparityMap = disparity.estimate(left, right);
			long endTime = System.currentTimeMillis();
			
			this.controller.addInternalModel(new ImageModel(disparityMap, left, model.getOriginalImage(), "Disparity Map", endTime - startTime));
			
		} catch (DisparityException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}