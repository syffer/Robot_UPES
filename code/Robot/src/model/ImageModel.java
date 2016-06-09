package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import transform.Pair;
import transform.Transformation;


public abstract class ImageModel {
	
	protected int height;
	protected int width;
	protected int[][] matrix;
	
	public ImageModel(int width, int heigth, int[][] data) {
		this.height = heigth;
		this.width = width;
		this.matrix = data;
	}
	
	public ImageModel(int[][] data) {
		this(data.length, data[0].length, data);
	}
	
	public ImageModel(int width, int height) {
		this(width, height, new int[width][height]);
	}
	
	public abstract void accept(Transformation transformation);
	
	public abstract BufferedImage getBufferedImage();
			
	

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	
	public int get(int i, int j) {
		return this.matrix[i][j];
	}
	
	
	public Set<Pair<Integer, Integer>> getIndexNeighbor4Connexity(int i, int j) {
		
		Set<Pair<Integer, Integer>> neighborsIndex = new HashSet<Pair<Integer, Integer>>();
		
		for(int k = -1; k < 2; k++) {
			if(j+k >= 0 && j+k < this.width) neighborsIndex.add(new Pair<Integer, Integer>(i, j+k));
			if(i+k >= 0 && i+k < this.height) neighborsIndex.add(new Pair<Integer, Integer>(i+k, j));			
		}
				
		return neighborsIndex;
	}
	
	
	
	
	public void saveAs(String pathToFile) throws IOException {	
		this.saveAs(new File(pathToFile));
	}
	
	public void saveAs(File file) throws IOException {
		BufferedImage bufferedImage = this.getBufferedImage();
		ImageIO.write(bufferedImage, "jpg", file);
	}
	
	
	
	
	
	// TODO delete it 
	public List<Set<Integer>> toSequence() {
		
		List<Set<Integer>> sequence = new ArrayList<Set<Integer>>();
		
		for(int i = 0; i < this.getWidth(); i++) {
			for(int j = 0; j < this.getHeight(); j++) {
				Set<Integer> set = new HashSet<Integer>();
				set.add(this.get(i,  j));
				sequence.add(set);
			}
		}
				
		return sequence;
	}
	
}
