package gui.controller;

import gui.model.HistogramModel;
import gui.model.ImageModel;
import image.Histogram;
import image.Image;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;


public class ActionHistogram extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionHistogram(GeneralController controller) {
		super("Histogram");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		ImageModel imageModel = (ImageModel) this.controller.getSelectedInternalModel();
		Image image = imageModel.getImage();
		
		Histogram histogram = new Histogram();
		
		image.accept(histogram);
		
		HistogramModel histogramModel = new HistogramModel(histogram);
		this.controller.addInternalModel(histogramModel); 
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.hasImageModelSelected());
	} 
}
