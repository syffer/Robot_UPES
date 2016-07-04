package gui.controller;

import gui.model.HistogramModel;
import gui.view.HistogramView;

public class HistogramController extends InternalController {

	protected HistogramController(HistogramModel histogramModel) {
		super(histogramModel, new HistogramView(histogramModel));
	}

}
