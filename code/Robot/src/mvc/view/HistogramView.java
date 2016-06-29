package mvc.view;

import mvc.model.Histogram;

public class HistogramView extends InternalView {
	private static final long serialVersionUID = 1L;

	private JHistogram jhistogram;
	
	public HistogramView(Histogram histogram) {
		super(histogram);

		this.jhistogram = new JHistogram(histogram.getFrequenciesMap(), histogram.getColors());
		
		this.add(this.jhistogram);
		
		
		this.setClosable(true);
		this.setResizable(true);
		this.setMaximizable(true); 
		
		this.pack();
		this.setVisible(true);
	}

}
