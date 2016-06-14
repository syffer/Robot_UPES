package transform.filter;

import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

public class Laplacian extends AbstractFilter {

	private static final int[][] mask1 = {{ 0, -1,  0}, 
										  {-1,  4, -1}, 
										  { 0, -1,  0}};
	
	private static final int[][] mask2 = {{-1, -1, -1}, 
										  {-1,  8, -1}, 
										  {-1, -1, -1}};
	
	public enum Type {
		MASK_1,
		MASK_2,
	}
	
	
	private final int[][] mask;
	
	public Laplacian(int[][] mask) {
		this.mask = mask;
	}
	
	public Laplacian(Type typeMask) {
		this((typeMask == Type.MASK_1) ? Laplacian.mask1 : Laplacian.mask2);
	}
	
	public Laplacian() {
		this(Laplacian.mask2);
	}
	
	
	@Override
	public void apply(RGBImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}

	@Override
	public void apply(GreyImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}

	@Override
	public void apply(MonoImage image) {
		this.imageTransformed = new GreyImage(this.filter(image));
	}
	
	
	private int[][] filter(Image image) {
		
		int[][] newData = new int[image.getWidth()][image.getHeight()];
		
		for(int i = 1; i < image.getWidth() - 2; i++) {
			for(int j = 1; j < image.getHeight() - 2; j++) {
				
				int value = mask[0][0] * image.get(i-1, j-1) + mask[0][1] * image.get(i, j-1) + mask[0][2] * image.get(i+1, j-1) + 
							mask[1][0] * image.get(i-1, j)   + mask[1][1] * image.get(i, j)   + mask[1][2] * image.get(i+1, j) +
							mask[2][0] * image.get(i-1, j+1) + mask[2][1] * image.get(i, j+1) + mask[2][2] * image.get(i+1, j+1);
				
				newData[i][j] = Math.min(255, Math.max(0, value));
			}
		}
		
		return newData;
	}
	
}
