package transform;

import java.util.ArrayList;
import java.util.List;

import geometry.Position;
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
	
	
	public void visit(SegmentedImage image) {
		
		int nbThreadsMax = 20;
		int nbThreads = Math.min(image.getNbSubImages(), nbThreadsMax);
		List<Thread> threads = new ArrayList<Thread>(nbThreads);
		
		SegmentedImage segmentedImage = new SegmentedImage(image.getWidth(),  image.getHeight(), image.getBlockSize());
		int nbImagesPerThreads = image.getNbSubImages() / nbThreads;
		int remainingImages = image.getNbSubImages() % nbThreads;
		int previousImagesNumer = 0;
		
		// run each threads 
		for(int numThread = 0; numThread < nbThreads - 1; numThread++) {
			int nbImagesForThisThread = nbImagesPerThreads;
			if(remainingImages > 0) {
				nbImagesForThisThread++;
				remainingImages--;
			}
			
			Transformation clone = this.clone();
			
			List<Position> positions = new ArrayList<Position>();
			for(int i = previousImagesNumer; i < previousImagesNumer + nbImagesForThisThread; i++) { 
				int u = i % image.getNbImagesWidth();
				int v = i / image.getNbImagesWidth();
				positions.add(new Position(u, v));
			}
			previousImagesNumer += nbImagesForThisThread;
			
			ThreadSegmentedImage thread = new ThreadSegmentedImage(numThread, clone, image, positions, segmentedImage);
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
		processSegmentedImage(this.clone(), image, positions, segmentedImage);
		
		// wait for the other threads 
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.setTransformedImage(segmentedImage);	
	}
		
	
	@Override 
	public Transformation clone() {
		Transformation clone = (Transformation) super.clone();
		clone.imageTransformed = null;
		return clone;
	}
	
	
	private static void processSegmentedImage(Transformation transformation, SegmentedImage imageOrigin, List<Position> positions, SegmentedImage imageTransformed) {
		for(Position p : positions) {
			Image image = imageOrigin.getImage(p.i, p.j);
			image.accept(transformation);
			imageTransformed.setImage(p.i, p.j, transformation.getTransformedImage());
		}
	}
	
	
	public static class ThreadSegmentedImage extends Thread {
		
		private int numero;
		private Transformation transformation;
		private SegmentedImage imageOrigin;
		private SegmentedImage imageTransformed;
		private List<Position> positions;
		
		public ThreadSegmentedImage(int numero, Transformation transformation, SegmentedImage imageOrigin, List<Position> positions, SegmentedImage imageTransformed) {
			this.numero = numero;
			this.transformation = transformation;
			this.imageOrigin = imageOrigin;
			this.imageTransformed = imageTransformed;
			this.positions = positions;
		}
		
		public void run() {
			processSegmentedImage(this.transformation, this.imageOrigin, this.positions, this.imageTransformed);
		}
		
		@Override 
		public String toString() {
			return "[" + this.numero + "] ";
		}
	}
	
}
