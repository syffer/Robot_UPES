package gui.view;

import gui.model.HistogramModel;

public class HistogramView extends InternalView {
	private static final long serialVersionUID = 1L;

	private JHistogram jhistogram;
	
	public HistogramView(HistogramModel histogramModel) {
		super(histogramModel);

		this.jhistogram = new JHistogram(histogramModel.getFrequenciesMap(), histogramModel.getColors());
		
		this.add(this.jhistogram);
		
		
		this.setClosable(true);
		this.setResizable(true);
		this.setMaximizable(true); 
		
		this.pack();
		this.setVisible(true);
	}

}
