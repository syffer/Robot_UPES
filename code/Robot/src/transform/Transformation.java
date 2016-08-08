package transform;

import java.util.ArrayList;
import java.util.List;

import geometry.Position;
import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.RGBImage;
import image.SegmentedImage;
import image.SegmentedImageGrey;
import image.SegmentedImageMono;
import image.SegmentedImageRGB;
import image.VisitorImage;

/**
 * Applies a transformation to an image (filter, threshold, ...). 
 * @author Maxime PINEAU
 * @see VisitorImage 
 */
public abstract class Transformation extends VisitorImage {

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
	


	@Override
	public void visit(SegmentedImageRGB image) { 
		SegmentedImageRGB segmentedImage = new SegmentedImageRGB(image.getWidth(), image.getHeight(), image.getBlockSize());
		this.visit(image, segmentedImage);
		this.setTransformedImage(segmentedImage);
	}

	@Override
	public void visit(SegmentedImageGrey image) {
		SegmentedImageGrey segmentedImage = new SegmentedImageGrey(image.getWidth(), image.getHeight(), image.getBlockSize());
		this.visit(image, segmentedImage);
		this.setTransformedImage(segmentedImage);
	}

	@Override
	public void visit(SegmentedImageMono image) {
		SegmentedImageMono segmentedImage = new SegmentedImageMono(image.getWidth(), image.getHeight(), image.getThreshold(), image.getBlockSize());
		this.visit(image, segmentedImage);
		this.setTransformedImage(segmentedImage);
	}
	
	
	public <I extends Image, J extends Image> SegmentedImage<J> visit(SegmentedImage<I> image, SegmentedImage<J> segmentedImage) {
		
		int nbThreads = Math.min(image.getNbImages(), ThreadSegmentedImage.nbThreadsMax);
		List<Thread> threads = new ArrayList<Thread>(nbThreads);
		
		//SegmentedImage<I> segmentedImage = new SegmentedImage<I>(image.getWidth(),  image.getHeight(), image.getBlockSize());
		int nbImagesPerThreads = image.getNbImages() / nbThreads;
		int remainingImages = image.getNbImages() % nbThreads;
		int previousImagesNumer = 0;
		
		// run each threads 
		for(int numThread = 0; numThread < nbThreads - 1; numThread++) {
			int nbImagesForThisThread = nbImagesPerThreads;
			if(remainingImages > 0) {
				nbImagesForThisThread++;
				remainingImages--;
			}
			
			Transformation clone = this.clone();
			
			// determinate the sub images of for this thread 
			List<Position> positions = new ArrayList<Position>();
			for(int i = previousImagesNumer; i < previousImagesNumer + nbImagesForThisThread; i++) { 
				int u = i % image.getNbImagesWidth();
				int v = i / image.getNbImagesWidth();
				positions.add(new Position(u, v));
			}
			previousImagesNumer += nbImagesForThisThread;
			
			ThreadSegmentedImage<I, J> thread = new ThreadSegmentedImage<I, J>(numThread, clone, image, positions, segmentedImage);
			thread.start();
			threads.add(thread);
		}
		
		// run this thread too 
		List<Position> positions = new ArrayList<Position>();
		for(int i = previousImagesNumer; i < previousImagesNumer + nbImagesPerThreads; i++) { 
			int u = i % image.getNbImagesWidth();
			int v = i / image.getNbImagesWidth();
			positions.add(new Position(u, v));
		}
		Transformation.processSegmentedImage(this.clone(), image, positions, segmentedImage);
		
		// wait for the other threads 
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return segmentedImage;
	}
		
	
	@Override 
	public Transformation clone() {
		Transformation clone = (Transformation) super.clone();
		clone.imageTransformed = null;
		return clone;
	}
	
	
	@SuppressWarnings("unchecked")
	private static <I extends Image, J extends Image> void processSegmentedImage(Transformation transformation, SegmentedImage<I> imageOrigin, List<Position> positions, SegmentedImage<J> imageTransformed) {
		for(Position p : positions) {
			I image = imageOrigin.getImage(p.i, p.j);
			image.accept(transformation);
			imageTransformed.setImage(p.i, p.j, (J) transformation.getTransformedImage()); 	
		}
	}
	
	
	public static class ThreadSegmentedImage<I extends Image, J extends Image> extends Thread {
		
		public static final int nbThreadsMax = 300;
		
		private int numero;
		private Transformation transformation;
		private SegmentedImage<I> imageOrigin;
		private SegmentedImage<J> imageTransformed;
		private List<Position> positions;
		
		public ThreadSegmentedImage(int numero, Transformation transformation, SegmentedImage<I> imageOrigin, List<Position> positions, SegmentedImage<J> imageTransformed) {
			this.numero = numero;
			this.transformation = transformation;
			this.imageOrigin = imageOrigin;
			this.imageTransformed = imageTransformed;
			this.positions = positions;
		}
		
		public void run() {
			Transformation.processSegmentedImage(this.transformation, this.imageOrigin, this.positions, this.imageTransformed);
		}
		
		@Override 
		public String toString() {
			return "[" + this.numero + "] ";
		}
	}

	
}
