package transform;

import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.RGBImage;
import image.SegmentedImage;
import image.VisitorImage;

/**
 * Applies a transformation to an image (filter, threshold, ...). 
 * @author Maxime PINEAU
 * @see VisitorImage 
 */
public abstract class Transformation implements VisitorImage {

	private Image imageTransformed;
	
	/**
	 * Returns the transformed image (i.e. the obtained image after applying the transformation) 
	 * @return the transofmed image 
	 */
	public Image getTransformedImage() {
		return this.imageTransformed;
	}
	
	public void setTransformedImage(Image image) {
		this.imageTransformed = image;
	}
	
	public abstract void visit(RGBImage image);
	public abstract void visit(GreyImage image);
	public abstract void visit(MonoImage image);
	
	
	public void visit(SegmentedImage image) {
		Image[][] subImages = new Image[image.getNbImagesWidth()][image.getNbImagesHeight()];

		for(int u = 0; u < image.getNbImagesWidth(); u++) {
			for(int v = 0; v < image.getNbImagesHeight(); v++) {
				Image subImage = image.getImage(u, v);
				subImage.accept(this);
				subImages[u][v] = this.getTransformedImage();
			}
		}
		
		this.setTransformedImage(new SegmentedImage(image.getWidth(), image.getHeight(), subImages, image.getBlockSize()));	
	}
	
}
