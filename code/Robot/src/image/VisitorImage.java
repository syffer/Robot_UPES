package image;


/**
 * The visitor pattern is used to separate the data structure from the operations. 
 * @author Maxime PINEAU
 * @see image.Image 
 */
public interface VisitorImage {
	
	/**
	 * Visits a RGB image. Applies the operation on a RGB image. 
	 * @param image the RGB image 
	 */
	public void apply(RGBImage image);
	
	
	/**
	 * Visits a grey image. Applies the operation on a grey image. 
	 * @param image the grey image 
	 */
	public void apply(GreyImage image);
	
	
	/**
	 * Visits a monochromatic image. Applies the operation on a monochromatic image. 
	 * @param image the monochromatic image 
	 */
	public void apply(MonoImage image);
}
