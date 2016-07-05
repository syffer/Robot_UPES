package gui.model;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import image.Histogram;

/**
 * Represents an internal model that contains the histogram of an image 
 * @see image.Histogram 
 * @author Maxime PINEAU
 */
public class HistogramModel extends InternalModel {

	private Histogram histogram;
	
	/**
	 * Creates a new HistogramModel from an existing Histogram 
	 * @param histogram the histogram 
	 */
	public HistogramModel(Histogram histogram) {
		this.histogram = histogram;
	}
	
	/**
	 * Returns the list of frequencies of the histogram.
	 * Each channel (e.g. RGB) has his own frequencies map.
	 * @see image.Histogram#getFrequenciesMap()
	 * @return the list of frequencies of the histogram
	 */
	public List<Map<Integer, Integer>> getFrequenciesMap() {
		return this.histogram.getFrequenciesMap();
	}
	
	/** 
	 * Returns the colors to be used for representing each channels differently 
	 * @see image.Histogram#getColors()
	 * @return the colors to be used for representing the channels 
	 */
	public List<Color> getColors() {
		return this.histogram.getColors();
	}
	
}
