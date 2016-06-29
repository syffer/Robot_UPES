package mvc.controller;

import mvc.model.Histogram;
import mvc.view.HistogramView;

public class HistogramController extends InternalController {

	protected HistogramController(Histogram histogram) {
		super(histogram, new HistogramView(histogram));
	}

}
