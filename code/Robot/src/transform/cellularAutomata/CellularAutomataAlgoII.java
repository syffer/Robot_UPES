package transform.cellularAutomata;

import geometry.Position;
import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.RGBImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class CellularAutomataAlgoII extends AbstractCellularAutomata {

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
		
		// the update rule is only applied to nonboundary cells 
		for(int i = 0; i < image.getWidth(); i++) {
			newData[i][0] = image.getRGB(i, 0);
			newData[i][image.getHeight() - 1] = image.getRGB(i, image.getHeight() - 1);
		}
		
		for(int j = 0; j < image.getHeight(); j++) {
			newData[0][j] = image.getRGB(0, j);
			newData[image.getHeight() - 1][j] = image.getRGB(image.getHeight() - 1, j);
		}
		
		// apply 
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				Set<Position> neighbors = image.getMooreNeighborhoods(1, i, j);
				List<Integer> values = new ArrayList<Integer>();
				
				for(Position pair : neighbors) {
					values.add(image.getRGB(pair.i, pair.j));
				}
				
				Collections.sort(values);
				
				// between 1 and size - 1 to eliminate the first and last values 
				double avg = 0;
				for(int k = 1; k < values.size() - 1; k++) {
					avg += values.get(k);
				}
				avg /= values.size() - 2;	// -2 because we eliminated 2 values 
				
				// apply rule 511 (?) 
				newData[i][j] = (int) avg;
			}
		}
		
		return newData;
	}

}
