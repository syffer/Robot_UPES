package transform.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.image.GreyImage;
import model.image.MonoImage;
import model.image.Pixel;
import model.image.RGBImage;

public class MedianFilter extends AbstractFilter {

	
	
	@Override
	public void apply(RGBImage image) {
		// TODO Auto-generated method stub
		
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
						int rgb = image.get(x + fx - edgex, y + fy - edgey);
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
				
				newData[x][y] = (new Pixel(windowRed.get(3*3/2), windowGreen.get(3*3/2), windowBlue.get(3*3/2))).getRGB();
			}
		}
		
		this.imageTransformed = new RGBImage(newData);	
		
		
	}

	@Override
	public void apply(GreyImage image) {
		// TODO Auto-generated method stub
		
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
				newData[x][y] = window.get(3 * 3 / 2);
			}
		}
		
		this.imageTransformed = new GreyImage(newData);		
	}

	@Override
	public void apply(MonoImage image) {
		// TODO Auto-generated method stub
		
	}

}
