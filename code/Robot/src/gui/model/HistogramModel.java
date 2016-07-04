package gui.model;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import image.Histogram;

public class HistogramModel extends InternalModel {

	private Histogram histogram;
	
	public HistogramModel(Histogram histogram) {
		this.histogram = histogram;
	}
	
	public List<Map<Integer, Integer>> getFrequenciesMap() {
		return this.histogram.getFrequenciesMap();
	}
	
	public List<Color> getColors() {
		return this.histogram.getColors();
	}
	
}
