package transform.symbolic;

import geometry.Position;
import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

import java.util.Set;

import transform.Transformation;


/**
 * Applies the CLAP algorithm to an image
 * @author Maxime PINEAU
 */
public class Clap extends Transformation {

	private int treashold;
	
	/**
	 * 
	 * @param treashold the given threshold 
	 */
	public Clap(int treashold) {
		this.treashold = treashold;
	}
	
	@Override
	public void visit(RGBImage image) {
		
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
				Set<Position> neighborsIndex = image.getVonNeumannNeighborhoods(1,  i,  j);
				//image.getIndexNeighbor4Connexity(i, j);
				
				
				for(Position p : neighborsIndex) {
					Pixel neighbor = image.getPixel(p.i, p.j);
					
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
					newData[i][j] = image.getRGB(i, j);
				}
				else {
					for(Position p : neighborsIndex) {
						
						Pixel neighbor = image.getPixel(p.i, p.j);
						
						if(redD <= this.treashold) neighbor.setRed(0);
						if(greenD <= this.treashold) neighbor.setGreen(0);
						if(blueD <= this.treashold) neighbor.setBlue(0);
						
						newData[p.i][p.j] = neighbor.getRGB();
					}
				}
			}
			
		}
		
		
		this.setTransformedImage(new RGBImage(newData));
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(new GreyImage(this.process(image)));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(new MonoImage(this.process(image), image.getThresholdValue()));
	}

	
	private int[][] process(GreyImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				int maximum = newData[i][j];
				int minimum = newData[i][j];
				
				// bornes 
				Set<Position> neighborsIndex = image.getVonNeumannNeighborhoods(1,  i,  j);
				//image.getIndexNeighbor4Connexity(i, j);
				
				
				for(Position p : neighborsIndex) {
					maximum = Math.max(maximum, image.get(p.i, p.j));
					minimum = Math.min(minimum, image.get(p.i, p.j));
				}
				
				int d = maximum - minimum;
								
				if(d <= this.treashold) {
					for(Position p : neighborsIndex) {
						newData[p.i][p.j] = 0;
					}
				}
				else {
					newData[i][j] = image.get(i, j);
				}
			}
		}
		
		return newData;
	}
	
		
	/*
	@Override
	public Clap clone() {
		return (Clap) super.clone();
	}
	*/
}
