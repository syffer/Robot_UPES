package controller;

import model.Histogram;
import view.HistogramView;

public class HistogramController extends InternalController {

	protected HistogramController(Histogram histogram) {
		super(histogram, new HistogramView(histogram));
	}

}
