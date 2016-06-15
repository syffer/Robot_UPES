package model;

import model.image.GreyImage;

public class GreyHistogram {//extends Histogram {

	private int[] frequencies;
	private int maximalFrequency;
	
	public GreyHistogram(GreyImage image) {
		
		this.frequencies = new int[256];
		this.maximalFrequency = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int greyValue = image.get(i, j);
				this.frequencies[greyValue]++;
				
				if(this.frequencies[greyValue] > this.maximalFrequency) this.maximalFrequency = this.frequencies[greyValue];
			}
		}
		
	}
	
	
}
