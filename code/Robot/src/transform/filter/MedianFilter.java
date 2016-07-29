package transform.filter;

import image.GreyImage;
import image.MonoImage;
import image.Pixel;
import image.RGBImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Applies a Median filter to an image. 
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Median_filter">https://en.wikipedia.org/wiki/Median_filter</a>
 * @author Maxime PINEAU
 */
public class MedianFilter extends AbstractFilter {
	
	@Override
	public void visit(RGBImage image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		int edgex = 3 / 2;
		int edgey = 3 / 2;
		
		for(int x = edgex; x < image.getWidth() - edgex; x++) {
			for(int y = edgey; y < image.getHeight() - edgey; y++) {
				
				List<Integer> windowRed = new ArrayList<Integer>();
				List<Integer> windowGreen = new ArrayList<Integer>();
				List<Integer> windowBlue = new ArrayList<Integer>();
				
				for(int fx = 0; fx < 3; fx++) {
					for(int fy = 0; fy < 3; fy++) {
						int rgb = image.getRGB(x + fx - edgex, y + fy - edgey);
						Pixel pixel = new Pixel(rgb);
						
						int red = pixel.getRed();
						int green = pixel.getGreen();
						int blue = pixel.getBlue();
						
						windowRed.add(red);
						windowGreen.add(green);
						windowBlue.add(blue);
					}
				}
				
				Collections.sort(windowRed);
				Collections.sort(windowGreen);
				Collections.sort(windowBlue);
				
				// get the median value 
				newData[x][y] = (new Pixel(windowRed.get(3*3/2), windowGreen.get(3*3/2), windowBlue.get(3*3/2))).getRGB();
			}
		}
		
		this.setTransformedImage(new RGBImage(newData));	
		
		
	}

	@Override
	public void visit(GreyImage image) {
		this.setTransformedImage(new GreyImage(this.filterWithOneDimension(image)));		
	}

	@Override
	public void visit(MonoImage image) {
		this.setTransformedImage(new MonoImage(this.filterWithOneDimension(image), image.getThresholdValue()));
	}

	private int[][] filterWithOneDimension(GreyImage image) {
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		int edgex = 3 / 2;
		int edgey = 3 / 2;
		
		for(int x = edgex; x < image.getWidth() - edgex; x++) {
			for(int y = edgey; y < image.getHeight() - edgey; y++) {
				
				List<Integer> window = new ArrayList<Integer>();
				
				for(int fx = 0; fx < 3; fx++) {
					for(int fy = 0; fy < 3; fy++) {
						int pixelValue = image.get(x + fx - edgex, y + fy - edgey);
						window.add(pixelValue);
					}
				}
				
				Collections.sort(window);
				
				// get the median value 
				newData[x][y] = window.get(3 * 3 / 2);
			}
		}
		
		return newData;
	}
	
}
