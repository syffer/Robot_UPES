package image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SegmentedImageMono extends AbstractMonoImage implements SegmentedImage<MonoImage> {
	
	private int blockSize;
	protected MonoImage[][] images;
	
	protected int nbImagesWidth;
	protected int nbImagesHeight;
	
	protected SegmentedImageMono(int width, int height, int threshold, MonoImage[][] images, int blockSize) {
		super(width, height, threshold);
		this.blockSize = blockSize;
		this.images = images;
		this.nbImagesWidth = (int) Math.ceil((double)width / (double)blockSize);
		this.nbImagesHeight = (int) Math.ceil((double)height / (double)blockSize);
	}
	
	public SegmentedImageMono(int width, int height, int threshold, int blockSize) {
		this(width, height, threshold, new MonoImage[(int) Math.ceil((double)width / (double)blockSize)][(int) Math.ceil((double)height / (double)blockSize)], blockSize);
	}
	
	public SegmentedImageMono(MonoImage image, int blockSize) {
		this(image.getWidth(), image.getHeight(), image.getThreshold(), blockSize);
		
		for(int i = 0; i < image.getWidth(); i += blockSize) {
			for(int j = 0; j < image.getHeight(); j += blockSize) {
				
				int widthSubImage = (image.getWidth() - i > blockSize) ? blockSize : image.getWidth() - i;
				int heightSubImage = (image.getHeight() - j > blockSize) ? blockSize : image.getHeight() - j;
				
				MonoImage subImage = image.getSubImage(i, j, widthSubImage, heightSubImage);
				
				this.setImage(i / blockSize, j / blockSize, subImage);
			}
		}
	}

	@Override
	public MonoImage getImage(int u, int v) {
		return this.images[u][v];
	}

	@Override
	public void setImage(int u, int v, MonoImage image) {
		this.images[u][v] = image;
	}
	
	
	
	@Override
	public Pixel getPixel(int i, int j) {
		SegmentedImage.IndexImage index = SegmentedImage.IndexImage.getSubImageIndexFromIndex(i, j, this.blockSize);
		Image image = this.getImage(index.u, index.v);
		return image.getPixel(index.x, index.y);
	}

	@Override
	public void set(int i, int j, Pixel pixel) {
		SegmentedImage.IndexImage index = SegmentedImage.IndexImage.getSubImageIndexFromIndex(i, j, this.blockSize);
		Image image = this.getImage(index.u, index.v);
		image.set(index.x, index.y, pixel);
	}
	
	
	@Override
	public int get(int i, int j) {
		SegmentedImage.IndexImage index = SegmentedImage.IndexImage.getSubImageIndexFromIndex(i, j, this.blockSize);
		MonoImage image = this.getImage(index.u, index.v);
		return image.get(index.x,  index.y);
	}
	
	@Override
	public void set(int i, int j, int grey) {
		SegmentedImage.IndexImage index = SegmentedImage.IndexImage.getSubImageIndexFromIndex(i, j, this.blockSize);
		MonoImage image = this.getImage(index.u, index.v);
		image.set(index.x,  index.y, grey);
	}
	

	@Override
	public int getBlockSize() {
		return this.blockSize;
	}

	@Override
	public int getNbImages() {
		return this.nbImagesWidth * this.nbImagesHeight;
	}

	@Override
	public int getNbImagesWidth() {
		return this.nbImagesWidth;
	}

	@Override
	public int getNbImagesHeight() { 
		return this.nbImagesHeight;
	}
	
	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.visit(this);
	}

	@Override
	public Image getSubImage(int iStart, int jStart, int width, int height) {
		int[][] data = new int[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				data[i][j] = this.get(i + iStart, j + jStart);
			}
		}
		return new MonoImage(data, this.threshold);
	}
	
	

	@Override
	public SegmentedImageMono clone() {
		SegmentedImageMono clone = (SegmentedImageMono) super.clone();
		
		clone.images = new MonoImage[clone.nbImagesWidth][clone.nbImagesHeight];
		
		for(int u = 0; u < this.getNbImagesWidth(); u++) {
			for(int v = 0; v < this.getNbImagesHeight(); v++) {
				MonoImage image = this.getImage(u, v);
				MonoImage cloneImage = image.clone();
				clone.setImage(u, v, cloneImage);
			}
		}
		
		return clone;
	}


	@Override
	public BufferedImage getBufferedImageToShow() {
		
		int nbWidthGrid = this.nbImagesWidth - 1;
		int nbHeightGrid = this.nbImagesHeight - 1;
		
		int totalWidth = this.width + nbWidthGrid;
		int totalHeight = this.height + nbHeightGrid;
		
		// set background color (the grid)
		BufferedImage bufferedImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = bufferedImage.createGraphics();

		graphics.setPaint(SegmentedImage.DEFAULT_GRID_COLOR);
		graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		
		// fill the buffered image with the sub images
		for(int u = 0; u < this.nbImagesWidth; u++) {
			for(int v = 0; v < this.nbImagesHeight; v++) {
				Image image = this.getImage(u, v);
				
				if(image == null) System.out.println(u + " " + v + " " + image);
				
				for(int i = 0; i < image.getWidth(); i++) {
					for(int j = 0; j < image.getHeight(); j++) {
						Pixel pixel = image.getPixel(i, j); 
						int x = i + ((this.blockSize + 1) * u); 
						int y = j + ((this.blockSize + 1) * v);
						bufferedImage.setRGB(x, y, pixel.getRGB());
					}
				}
				
			}
		}
		
		return bufferedImage;
	}
	
}
