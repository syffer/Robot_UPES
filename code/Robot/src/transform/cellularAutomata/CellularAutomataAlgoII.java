package transform.cellularAutomata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import transform.Pair;
import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

public class CellularAutomataAlgoII extends AbstractCellularAutomata {

	@Override
	public void apply(RGBImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(GreyImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(MonoImage image) {
		// TODO Auto-generated method stub
		
	}
	
	private int[][] process(Image image) {
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		// the update rule is only applied to nonboundary cells 
		for(int i = 0; i < image.getWidth(); i++) {
			newData[i][0] = image.get(i, 0);
			newData[i][image.getHeight() - 1] = image.get(i, image.getHeight() - 1);
		}
		
		for(int j = 0; j < image.getHeight(); j++) {
			newData[0][j] = image.get(0, j);
			newData[image.getHeight() - 1][j] = image.get(image.getHeight() - 1, j);
		}
		
		// apply 
		for(int i = 1; i < image.getWidth() - 1; i++) {
			for(int j = 1; j < image.getHeight() - 1; j++) {
				
				Set<Pair<Integer, Integer>> neighbors = image.getMooreNeighborhoods(1, i, j);
				List<Integer> values = new ArrayList<Integer>();
				
				for(Pair<Integer, Integer> pair : neighbors) {
					values.add(image.get(pair.first, pair.second));
				}
				
				Collections.sort(values);
				
				double avg = 0;
				for(int k = 1; k < values.size() - 1; k++) {
					avg += values.get(k);
				}
				avg /= values.size() - 1;
				
				// apply rule 511 
				
				// newData[i][j] = ... 
			}
		}
		
		return newData;
	}

}
