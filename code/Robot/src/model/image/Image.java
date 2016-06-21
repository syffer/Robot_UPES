package model.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import transform.VisitorImage;


public abstract class Image {
	
	protected int height;
	protected int width;
	protected int[][] matrix;
	
	public Image(int width, int heigth, int[][] data) {
		this.height = heigth;
		this.width = width;
		this.matrix = data;
	}
	
	public Image(int[][] data) {
		this(data.length, data[0].length, data);
	}
	
	public Image(int width, int height) {
		this(width, height, new int[width][height]);
	}
	
	public abstract void accept(VisitorImage visitorImage);
	
	public abstract BufferedImage getBufferedImage();
			
	

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getNbPixels() {
		return this.width * this.height;
	}
	
	public int[][] getCloneMatrix() {
		int[][] clone = new int[this.width][this.height];
		
		for(int i = 0; i < this.width; i++) {
			clone[i] = this.matrix[i].clone();
		}
		
		return clone;
	}
	
	public boolean isInBound(int i, int j) {
		return i >= 0 && i < this.width && j >= 0 && j < this.height;
	}
	
	public int get(int i, int j) {
		return this.matrix[i][j];
	}
		
	
	public Set<Pair<Integer, Integer>> getMooreNeighborhoods(int range, int i, int j) {
		// 8 connexe 
		
		Set<Pair<Integer, Integer>> neighborsIndex = new HashSet<Pair<Integer, Integer>>();
		
		for(int k = i - range; k <= i + range; k++) {
			for(int l = j - range; l <= j + range; l++) {
				if(this.isInBound(k, l)) neighborsIndex.add(new Pair<Integer, Integer>(k, l));
			}
		}
		
		return neighborsIndex;
	}
	
	public Set<Pair<Integer, Integer>> getVonNeumannNeighborhoods(int range, int i, int j) {
		// 4 connexe 
		
		Set<Pair<Integer, Integer>> neighborsIndex = new HashSet<Pair<Integer, Integer>>();
		
		for(int k = i - range; k <= i + range; k++) {
			for(int l = j - range; l <= j + range; l++) {
				if(this.isInBound(k, l) && Math.abs(k - i) + Math.abs(l - j) <= range) {
					neighborsIndex.add(new Pair<Integer, Integer>(k, l));
				}
			}
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
	
		
}
