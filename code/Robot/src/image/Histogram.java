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
public class Histogram extends VisitorImage {

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
	public void visit(RGBImage image) {
		if(this.frequenciesMap.isEmpty()) {
			this.frequenciesMap.add(new TreeMap<Integer, Integer>());
			this.frequenciesMap.add(new TreeMap<Integer, Integer>());
			this.frequenciesMap.add(new TreeMap<Integer, Integer>());
		}
		
		Map<Integer, Integer> redFrequencies = this.frequenciesMap.get(0);
		Map<Integer, Integer> greenFrequencies = this.frequenciesMap.get(1);
		Map<Integer, Integer> blueFrequencies = this.frequenciesMap.get(2);
		
		if(this.colors.isEmpty()) {
			this.colors.add(Color.RED);
			this.colors.add(Color.GREEN);
			this.colors.add(Color.BLUE);
		}
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Pixel pixel = image.getPixel(i, j);
				
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
	}

	@Override
	public void visit(GreyImage greyImage) { 
		if(this.frequenciesMap.isEmpty()) this.frequenciesMap.add(new TreeMap<Integer, Integer>());
		Map<Integer, Integer> frequencies = this.frequenciesMap.get(0);
		
		if(this.colors.isEmpty()) this.colors.add(Color.GRAY);
		
		for(int i = 0; i < greyImage.getWidth(); i++) {
			for(int j = 0; j < greyImage.getHeight(); j++) {
				int greyValue = greyImage.get(i, j);
				
				if(!frequencies.containsKey(greyValue)) frequencies.put(greyValue, 0);
				
				int amount = frequencies.get(greyValue) + 1;
				frequencies.put(greyValue, amount);
			}
		}
		
		//this.frequenciesMap.add(frequencies);
		//this.colors.add(Color.GRAY);
	}

	@Override
	public void visit(MonoImage image) {		
		if(this.frequenciesMap.isEmpty()) this.frequenciesMap.add(new TreeMap<Integer, Integer>());
		Map<Integer, Integer> frequencies = this.frequenciesMap.get(0);
		
		if(this.colors.isEmpty()) this.colors.add(Color.GRAY);
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int greyValue = image.get(i, j);
				
				if(!frequencies.containsKey(greyValue)) frequencies.put(greyValue, 0);
				
				int amount = frequencies.get(greyValue) + 1;
				frequencies.put(greyValue, amount);
			}
		}
	}

	@Override
	public void visit(SegmentedImage image) {
		
		for(int u = 0; u < image.getNbImagesWidth(); u++) {
			for(int v = 0; v < image.getNbImagesHeight(); v++) {
				System.out.println(u + " " + v);
				Image subImage = image.getImage(u, v);
				subImage.accept(this);
			}
		}
		
	}
	
}
