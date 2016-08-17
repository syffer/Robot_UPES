package transform.cellularAutomata;

import geometry.Position;
import image.AbstractGreyImage;
import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class CellularAutomataAlgoII extends AbstractCellularAutomata {

	@Override
	public void visit(RGBImage image) {
		
		RGBImage newImage = image.clone();
		
		// apply 
		for(int i = 1; i < newImage.getWidth() - 1; i++) {
			for(int j = 1; j < newImage.getHeight() - 1; j++) {
				
				Set<Position> neighbors = image.getMooreNeighborhoods(1, i, j);
				
				List<Integer> reds = new ArrayList<Integer>();
				List<Integer> greens = new ArrayList<Integer>();
				List<Integer> blues = new ArrayList<Integer>();
				
				for(Position pair : neighbors) {
					Pixel pixel = image.getPixel(pair.i, pair.j);
					reds.add(pixel.getRed());
					greens.add(pixel.getGreen());
					blues.add(pixel.getBlue());
				}
				
				
				Collections.sort(reds);
				Collections.sort(greens);
				Collections.sort(blues);
				
				// between 1 and size - 1 to eliminate the first and last values 
				double avgRed = 0;
				double avgGreen = 0;
				double avgBlue = 0;
				
				for(int k = 1; k < reds.size() - 1; k++) {
					avgRed += reds.get(k);
					avgGreen += greens.get(k);
					avgBlue += blues.get(k);
				}
				avgRed /= reds.size() - 2;	// -2 because we eliminated 2 values 
				avgGreen /= reds.size() - 2;
				avgBlue /= reds.size() - 2;	
				
				
				// apply rule 511 (?) 
				newImage.set(i, j, new Pixel((int)avgRed, (int)avgGreen, (int)avgBlue));
			}
						
		}
		
		this.setTransformedImage(newImage);
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(this.process(image));
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(this.process(image));
	}
	
	private AbstractGreyImage process(AbstractGreyImage image) { 
		
		AbstractGreyImage greyImage = image.clone();	
		// the update rule is only applied to nonboundary cells 
		
		
		// apply 
		for(int i = 1; i < greyImage.getWidth() - 1; i++) {
			for(int j = 1; j < greyImage.getHeight() - 1; j++) {
				
				Set<Position> neighbors = image.getMooreNeighborhoods(1, i, j);
				List<Integer> values = new ArrayList<Integer>();
				
				for(Position pair : neighbors) {
					values.add(image.get(pair.i, pair.j));
				}
				
				Collections.sort(values);
				
				// between 1 and size - 1 to eliminate the first and last values 
				double avg = 0;
				for(int k = 1; k < values.size() - 1; k++) {
					avg += values.get(k);
				}
				avg /= values.size() - 2;	// -2 because we eliminated 2 values 
								
				// apply rule 511 (?) 
				greyImage.set(i, j, (int) avg);
			}
		}
		
		return greyImage;
	}

}
