package gui.controller;

import gui.model.HistogramModel;
import gui.view.HistogramView;

/**
 * Controller of the histogram model and view.
 * Creates the histogram view.
 * @see image.Histogram
 * @author Maxime PINEAU
 */
public class HistogramController extends InternalController {

	protected HistogramController(HistogramModel histogramModel) {
		super(histogramModel, new HistogramView(histogramModel));
	}

}
