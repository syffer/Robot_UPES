package image;

import java.awt.Color;

public interface SegmentedImage<I extends Image> {

	public static final int DEFAULT_BLOCK_SIZE = 200; //805
	public static final Color DEFAULT_GRID_COLOR = Color.MAGENTA;
	
	public I getImage(int u, int v);
	public void setImage(int u, int v, I image);
	public int getBlockSize();
	
	public int getNbImages();
	public int getNbImagesWidth();
	public int getNbImagesHeight();
	
	
	public static class IndexImage {
		public int i, j;
		public int u, v;
		public int x, y;
		
		public IndexImage(int i, int j, int blockSize) {
			this.i = i;
			this.j = j;
			
			this.u = i / blockSize;
			this.v = i / blockSize;
			
			this.x = i % blockSize;
			this.y = j % blockSize;
		}
		
		public IndexImage(int u, int v, int x, int y, int blockSize) {
			this.i = (u * blockSize) + x;
			this.j = (v * blockSize) + y;
			
			this.u = u;
			this.v = v;
			
			this.x = x;
			this.y = y;
		}
		
		public static IndexImage getSubImageIndexFromIndex(int i, int j, int blockSize) {
			return new IndexImage(i, j, blockSize);
		}
		
		public static IndexImage getIndexFromSubImageIndex(int u, int v, int x, int y, int blockSize) {
			return new IndexImage(u, v, x, y, blockSize);
		}
		
		public static IndexImage getIndexFromSubImageIndex(int u, int v, int blockSize) {
			return new IndexImage(u, v, 0, 0, blockSize);
		}
		
	}
	
	
}
