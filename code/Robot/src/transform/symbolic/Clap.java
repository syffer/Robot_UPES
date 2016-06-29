package transform.symbolic;

import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.Pair;
import image.Pixel;
import image.RGBImage;

import java.util.Set;

import transform.Transformation;


public class Clap extends Transformation {

	private int treashold;
	
	public Clap(int treashold) {
		this.treashold = treashold;
	}
	
	@Override
	public void apply(RGBImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				Pixel pixel = new Pixel(newData[i][j]);
								
				int maxRed = pixel.getRed();
				int maxGreen = pixel.getGreen();
				int maxBlue = pixel.getBlue();
				
				int minRed = pixel.getRed();
				int minGreen = pixel.getGreen();
				int minBlue = pixel.getBlue();
				
				// bornes 
				Set<Pair<Integer, Integer>> neighborsIndex = image.getVonNeumannNeighborhoods(1,  i,  j);
				//image.getIndexNeighbor4Connexity(i, j);
				
				
				for(Pair<Integer, Integer> p : neighborsIndex) {
					Pixel neighbor = new Pixel(image.get(p.first, p.second));
					
					maxRed = Math.max(maxRed, neighbor.getRed());
					maxGreen = Math.max(maxGreen, neighbor.getGreen());
					maxBlue = Math.max(maxBlue, neighbor.getBlue());
					
					minRed = Math.min(minRed, neighbor.getRed());
					minGreen = Math.min(minGreen, neighbor.getGreen());
					minBlue = Math.min(minBlue, neighbor.getBlue());
				}
				
				int redD = maxRed - minRed;
				int greenD = maxGreen - minGreen;
				int blueD = maxBlue - minBlue;
				
				if(redD > this.treashold && greenD > this.treashold && blueD > this.treashold) {
					newData[i][j] = image.get(i, j);
				}
				else {
					for(Pair<Integer, Integer> p : neighborsIndex) {
						
						Pixel neighbor = new Pixel(image.get(p.first, p.second));
						
						if(redD <= this.treashold) neighbor.setRed(0);
						if(greenD <= this.treashold) neighbor.setGreen(0);
						if(blueD <= this.treashold) neighbor.setBlue(0);
						
						newData[p.first][p.second] = neighbor.getRGB();
					}
				}
			}
			
		}
		
		
		this.imageTransformed = new RGBImage(newData);
	}

	@Override
	public void apply(GreyImage image) {
		this.imageTransformed = new GreyImage(this.process(image));
	}

	@Override
	public void apply(MonoImage image) {
		this.imageTransformed = new GreyImage(this.process(image));
	}

	
	private int[][] process(Image image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				int maximum = newData[i][j];
				int minimum = newData[i][j];
				
				// bornes 
				Set<Pair<Integer, Integer>> neighborsIndex = image.getVonNeumannNeighborhoods(1,  i,  j);
				//image.getIndexNeighbor4Connexity(i, j);
				
				
				for(Pair<Integer, Integer> p : neighborsIndex) {
					maximum = Math.max(maximum, image.get(p.first, p.second));
					minimum = Math.min(minimum, image.get(p.first, p.second));
				}
				
				int d = maximum - minimum;
								
				if(d <= this.treashold) {
					for(Pair<Integer, Integer> p : neighborsIndex) {
						newData[p.first][p.second] = 0;
					}
				}
				else {
					newData[i][j] = image.get(i, j);
				}
			}
		}
		
		return newData;
	}
	
		
	
}
