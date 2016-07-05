package gui.controller;

import gui.model.ImageModel;
import gui.model.StatisticAnalysisInfo;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;


/**
 * Action that allows the user to perform a statistic analysis on an image.
 * @see gui.model.StatisticAnalysisInfo
 * @author Maxime PINEAU
 */
public class ActionStatisticAnalysis extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionStatisticAnalysis(GeneralController controller) {
		super("Statistic Analysis");
		
		this.controller = controller;
		controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
		Image image = imageModel.getImage();
		
		StatisticAnalysisInfo statisticAnalysisInfo;
		
		if(imageModel.hasOriginalImage()) statisticAnalysisInfo = new StatisticAnalysisInfo(image, imageModel.getOriginalImage());
		else statisticAnalysisInfo = new StatisticAnalysisInfo(image);
		
		this.controller.addInternalModel(statisticAnalysisInfo); 
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}