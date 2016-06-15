package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.image.GreyImage;

public class Histogram extends InternalModel {

	private List<Map<Integer, Integer>> frequenciesMap;
	private List<Color> colors;
	
	public Histogram(GreyImage greyImage) {
		
		this.frequenciesMap = new ArrayList<Map<Integer,Integer>>();
		
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
		
		
		this.colors = new ArrayList<Color>();
		this.colors.add(Color.GRAY);
		
	}
	
}
