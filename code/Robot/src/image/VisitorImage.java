package image;


/**
 * The visitor pattern is used to separate the data structure from the operations. 
 * @author Maxime PINEAU
 * @see image.Image 
 */
public abstract class VisitorImage implements Cloneable {
	
	/**
	 * Visits a RGB image. Applies the operation on a RGB image. 
	 * @param image the RGB image 
	 */
	public abstract void visit(RGBImage image);
	
	
	/**
	 * Visits a grey image. Applies the operation on a grey image. 
	 * @param image the grey image 
	 */
	public abstract void visit(GreyImage image);
	
	
	/**
	 * Visits a monochromatic image. Applies the operation on a monochromatic image. 
	 * @param image the monochromatic image 
	 */
	public abstract void visit(MonoImage image);
	
	
	public abstract void visit(SegmentedImage image);
	
	@Override
	public VisitorImage clone() {
		try {
			return (VisitorImage) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError("clonage imposible");
		}
	}
	
}
