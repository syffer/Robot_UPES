package gui.controller;

import gui.model.Histogram;
import gui.view.HistogramView;

public class HistogramController extends InternalController {

	protected HistogramController(Histogram histogram) {
		super(histogram, new HistogramView(histogram));
	}

}
