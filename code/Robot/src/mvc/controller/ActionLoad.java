package mvc.controller;

import image.Image;
import image.RGBImage;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;

import mvc.model.ImageModel;
import mvc.view.ChoiceCanceledException;


public class ActionLoad extends AbstractAction implements Observer {
	private static final long serialVersionUID = 7721629641991701632L;

	private GeneralController generalController;
	
	public ActionLoad(GeneralController generalController) {
		super("Load"); 
		
		this.generalController = generalController;
		generalController.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		try { 
			File choosenFile = this.generalController.view.getFileToLoad();
			
			long startTime = System.currentTimeMillis();
			BufferedImage bufferedImage = ImageIO.read(choosenFile);
			long endTime = System.currentTimeMillis();
			
			Image image = new RGBImage(bufferedImage);
			this.generalController.addInternalModel(new ImageModel(image, "Loading", endTime - startTime));
			
		} catch (ChoiceCanceledException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void update(Observable observable, Object params) {
		
	}
}