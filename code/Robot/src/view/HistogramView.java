package view;

import model.Histogram;

public class HistogramView extends InternalView {

	public HistogramView(Histogram histogram) {
		super(histogram);

		JHistogram jhistogram = new JHistogram(null);
	}

}
