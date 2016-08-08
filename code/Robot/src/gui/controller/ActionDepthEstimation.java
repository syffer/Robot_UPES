package gui.controller;

import gui.model.ImageModel;
import image.AbstractGreyImage;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import dimention3D.DepthEstimation;
import dimention3D.DepthEstimationException;

public class ActionDepthEstimation extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;
	
	private GeneralController controller;
	
	public ActionDepthEstimation(GeneralController generalController) {
		super("Depth estimmation"); 
		
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
			Image left = image.getSubImage(0, 0, image.getWidth() - 1, image.getHeight());
			Image right = image.getSubImage(1, 0, image.getWidth() - 1, image.getHeight());
			
			// get parameters from users....
			double focalLenght = 10;
			double baseLine = 30;
			
			long startTime = System.currentTimeMillis();
			DepthEstimation depthEstimation = new DepthEstimation(focalLenght, baseLine);
			AbstractGreyImage depthMap = depthEstimation.estimate(left, right);
			long endTime = System.currentTimeMillis();
			
			this.controller.addInternalModel(new ImageModel(depthMap, left, model.getOriginalImage(), "Depth estimation", endTime - startTime));
			
		} catch (DepthEstimationException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}