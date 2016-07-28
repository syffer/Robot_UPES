package transform.filter;

import image.GreyImage;
import image.MonoImage;
import image.RGBImage;

/**
 * Applies a gaussian blur on an image (Blur an image with a gaussian function). 
 * 
 * @see <a href="http://blog.ivank.net/fastest-gaussian-blur.html">http://blog.ivank.net/fastest-gaussian-blur.html</a>
 * @author Maxime PINEAU
 */
public class GaussianBlur extends AbstractFilter {
	
	private int radius;
	
	/**
	 * Creates a Gaussian blur with a radius of 1
	 */
	public GaussianBlur() {
		this(1);
	}
	
	/**
	 * Creates a Gaussian blur of a certain radius 
	 * @param radius the given radius 
	 */
	public GaussianBlur(int radius) {
		this.radius = radius;
	}
	
	
	@Override
	public void apply(RGBImage image) {
		// TODO Auto-generated method stub
		this.imageTransformed = null;
	}

	@Override
	public void apply(GreyImage image) {
		int[][] source = image.clone().getMatrix(); 	 
		int[][] target = new int[image.getWidth()][image.getHeight()];
		
		this.gaussBlur_4(source, target, image.getWidth(), image.getHeight(), this.radius);		
		this.imageTransformed = new GreyImage(target); 
	}

	@Override
	public void apply(MonoImage image) {
		int[][] source = image.clone().getMatrix(); 	 
		int[][] target = new int[image.getWidth()][image.getHeight()];
		
		this.gaussBlur_4(source, target, image.getWidth(), image.getHeight(), this.radius);		
		this.imageTransformed = new GreyImage(target); 
	}

	
	@SuppressWarnings("unused")
	private void gaussBlur_1(int[][] source, int[][] target, int width, int heigth, int radius) {
	    int rs = (int) Math.ceil(radius * 2.57);     // significant radius
	    for(int i=0; i<heigth; i++)
	        for(int j=0; j<width; j++) {
	            double val = 0, wsum = 0;
	            for(int iy = i-rs; iy<i+rs+1; iy++)
	                for(int ix = j-rs; ix<j+rs+1; ix++) {
	                	int x = Math.min(width-1, Math.max(0, ix));
	                	int y = Math.min(heigth-1, Math.max(0, iy));
	                	int dsq = (ix-j)*(ix-j)+(iy-i)*(iy-i);
	                	double wght = (Math.exp( -dsq / (2*radius*radius) ) / (Math.PI*2*radius*radius));
	                    val += source[x][y] * wght;  wsum += wght;
	                }
	            target[j][i] = (int) Math.round(val/wsum);            
	        }
	}
	
	
	
	private void gaussBlur_4(int[][] source, int[][] target, int width, int heigth, int radius) {
		int[] boxes = this.boxesForGauss(this.radius, 3); 
		this.boxBlur_4(source, target, width, heigth, (boxes[0] - 1) / 2); 	
		this.boxBlur_4(target, source, width, heigth, (boxes[1] - 1) / 2); 
		this.boxBlur_4(source, target, width, heigth, (boxes[2] - 1) / 2); 
	}
	
	
	private int[] boxesForGauss(int sigma, int nbBoxes) {
		double wIdeal = Math.sqrt((12 * sigma * sigma / nbBoxes) + 1);  // Ideal averaging filter width 
	    int wl = (int) Math.floor(wIdeal);  
	    if(wl % 2 == 0) wl--;
	    int wu = wl + 2;
		
	    double mIdeal = (12*sigma*sigma - nbBoxes*wl*wl - 4*nbBoxes*wl - 3*nbBoxes) / (-4*wl - 4);
	    int m = (int) Math.round(mIdeal);
	    // var sigmaActual = Math.sqrt( (m*wl*wl + (n-m)*wu*wu - n)/12 );
					
	    int[] sizes = new int[nbBoxes];  
	    for(int i = 0; i < nbBoxes; i++) {
	    	sizes[i] = (i < m) ? wl : wu;
	    }
	    
	    return sizes;
	}
	
	private void boxBlur_4(int[][] source, int[][] target, int width, int heigth, int radius) {
		
		for(int i = 0; i < width; i++) { 
			for(int j = 0; j < heigth; j++) {
				target[i][j] = source[i][j];
			}			
		}
		
		this.boxBlurH_4(target, source, width, heigth, radius);				
		this.boxBlurT_4(source, target, width, heigth, radius); 
	}
	
	private void boxBlurH_4(int[][] source, int[][] target, int width, int heigth, int radius) {
		double iarr = 1.0 / (radius + radius + 1.0);
				
		for(int j = 0; j < heigth; j++) {
			
			int ti = 0;			//int ti = i * width;
			int li = 0;			//int li = ti;
			int ri = radius;	//int ri = ti + radius;
			
			int fv = source[0][j]; 			// int ti = i * width;
			int lv = source[width - 1][j]; //int[] lv = source[ti + width - 1];
			double val = (radius + 1) * fv;
			
			for(int i = 0; i < radius; i++) val += source[i][j];
			
			for(int i = 0; i <= radius; i++) {
				val += source[ri++][j] - fv; //val += source[ri++][j] - fv;	//val += source[ri++] - fv; 
				target[ti++][j] = (int) Math.round(val * iarr); // ti++
			}
			
			for(int i = radius + 1; i < width - radius; i++) {
				val += source[ri++][j] - source[li++][j]; 
				target[ti++][j] = (int) Math.round(val * iarr);
			}
			
			for(int i = width - radius; i < width; i++) {
				val += lv - source[li++][j];
				target[ti++][j] = (int) Math.round(val * iarr);
			}
		}
		
	}
	
	private void boxBlurT_4(int[][] source, int[][] target, int width, int heigth, int radius) {
		double iarr = 1.0 / (radius + radius + 1.0);
	    
		for(int i = 0; i < width; i++) {
	    	
	    	int fv = source[i][0];
	    	int lv = source[i][heigth - 1];
	    	double val = (radius + 1) * fv;
	    	
	    	int ti = 0;
	    	int li = ti; 
	    	int ri = radius;	//int ri = ti + radius * width;
	    	
	    	for(int j = 0; j < radius; j++) {
	    		val += source[i][j]; // val += source[ti + j*width];
	    	}
	    	
	    	for(int j = 0; j <= radius; j++) {
	    		val += source[i][ri++] - fv;
	    		target[i][ti++] = (int) Math.round(val*iarr);  
	    	}
	    	
	    	for(int j = radius + 1; j < heigth - radius; j++) {
	    		val += source[i][ri++] - source[i][li++];
	    		target[i][ti++] = (int) Math.round(val*iarr);  
	    	}
	    	
	    	for(int j = heigth - radius; j < heigth; j++) {
	    		val += lv - source[i][li++];
	    		target[i][ti++] = (int) Math.round(val*iarr);  
	    	}
	    }
	}
	
}
