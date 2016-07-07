package image;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**  
 * Represents the histogram of an image (i.e. count the frequency / number of appearance of each pixel values).
 * 
 * Pixel frequencies will be stock in a map.
 * 
 * As an image can have multiple channels (e.g. RGB image which have 3 channels), 
 * the pixel frequencies of each channels will be stock separately, in a list (names "frequenciesMap").
 * 
 * Each channel can be represented with a different color. Those colors are stocked in the list "colors".
 * 
 * Grey and monochromatic images have only one channel, and are represented in grey. 
 * RGB images have 3 channels, which are respectively represented in red, green and blue.
 * 
 * @author Maxime PINEAU
 * @see image.Image
 * @see image.VisitorImage 
 */
public class Histogram implements VisitorImage {

	/**
	 * Contains the pixel frequencies of each channel
	 */
	private List<Map<Integer, Integer>> frequenciesMap;
	
	
	/**
	 * Contains the colors associated to the channels
	 */
	private List<Color> colors;
	
	
	public Histogram() {
		this.frequenciesMap = new ArrayList<Map<Integer,Integer>>();
		this.colors = new ArrayList<Color>();
	}
	
	/**
	 * Return a list containing the pixel frequencies of each channels. 
	 * The pixel frequencies of one channel are stocked in a map. 
	 * @return a list containing the pixel frequencies of each channel 
	 */
	public List<Map<Integer, Integer>> getFrequenciesMap() {
		return this.frequenciesMap;
	}
	
	/**
	 * @return the colors associated to the channels
	 */
	public List<Color> getColors() {
		return this.colors;
	}
	
	@Override
	public void apply(RGBImage image) {
		Map<Integer, Integer> redFrequencies = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> greenFrequencies = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> blueFrequencies = new TreeMap<Integer, Integer>();
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Pixel pixel = new Pixel(image.get(i, j));
				
				int red = pixel.getRed();
				int green = pixel.getGreen();
				int blue = pixel.getBlue();
				
				if(!redFrequencies.containsKey(red)) redFrequencies.put(red, 0);
				redFrequencies.put(red, redFrequencies.get(red) + 1);
				
				if(!greenFrequencies.containsKey(green)) greenFrequencies.put(green, 0);
				greenFrequencies.put(green, greenFrequencies.get(green) + 1);
				
				if(!blueFrequencies.containsKey(blue)) blueFrequencies.put(blue, 0);
				blueFrequencies.put(blue, blueFrequencies.get(blue) + 1);
			}
		}
		
		this.frequenciesMap.add(redFrequencies);
		this.frequenciesMap.add(greenFrequencies);
		this.frequenciesMap.add(blueFrequencies);
		
		this.colors.add(Color.RED);
		this.colors.add(Color.GREEN);
		this.colors.add(Color.BLUE);
	}

	@Override
	public void apply(GreyImage greyImage) { 
		Map<Integer, Integer> frequencies = new TreeMap<Integer, Integer>();
		for(int i = 0; i < greyImage.getWidth(); i++) {
			for(int j = 0; j < greyImage.getHeight(); j++) {
				int greyValue = greyImage.get(i, j);
				
				if(!frequencies.containsKey(greyValue)) frequencies.put(greyValue, 0);
				
				int amount = frequencies.get(greyValue) + 1;
				frequencies.put(greyValue, amount);
			}
		}
		
		this.frequenciesMap.add(frequencies);
		this.colors.add(Color.GRAY);
	}

	@Override
	public void apply(MonoImage image) {		
		Map<Integer, Integer> frequencies = new TreeMap<Integer, Integer>();
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int greyValue = image.get(i, j);
				
				if(!frequencies.containsKey(greyValue)) frequencies.put(greyValue, 0);
				
				int amount = frequencies.get(greyValue) + 1;
				frequencies.put(greyValue, amount);
			}
		}
		
		this.frequenciesMap.add(frequencies);
		this.colors.add(Color.GRAY);
	}
	
}
