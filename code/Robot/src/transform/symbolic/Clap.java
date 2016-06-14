package transform.symbolic;

import java.util.Set;

import transform.Pair;
import transform.Transformation;

import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

public class Clap extends Transformation {

	private int treashold;
	
	public Clap(int treashold) {
		this.treashold = treashold;
	}
	
	@Override
	public void apply(RGBImage image) {
		this.imageTransformed = new RGBImage(this.process(image));
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
