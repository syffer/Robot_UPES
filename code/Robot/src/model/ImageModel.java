package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;


public abstract class ImageModel {
	
	protected int height;
	protected int width;
	
	public ImageModel(int width, int heigth) {
		this.height = heigth;
		this.width = width;
	}
	
	public abstract BufferedImage getBufferedImage();
			
	public void saveAs(String pathToFile) throws IOException {	
		this.saveAs(new File(pathToFile));
	}
	
	public void saveAs(File file) throws IOException {
		BufferedImage bufferedImage = this.getBufferedImage();
		ImageIO.write(bufferedImage, "jpg", file);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	
	public abstract int get(int i, int j);
	
	public List<Set<Integer>> toSequence() {
		
		List<Set<Integer>> sequence = new ArrayList<Set<Integer>>();
		
		for(int i = 0; i < this.getWidth(); i++) {
			for(int j = 0; j < this.getHeight(); j++) {
				Set<Integer> set = new HashSet<Integer>();
				set.add(this.get(i,  j));
				sequence.add(set);
			}
		}
		
		// padding ?
		
		return sequence;
	}
	
}
