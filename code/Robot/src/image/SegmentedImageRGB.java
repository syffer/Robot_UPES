package image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SegmentedImageRGB extends AbstractRGBImage implements SegmentedImage<RGBImage> {
		
	private int blockSize;
	protected RGBImage[][] images;
	
	protected int nbImagesWidth;
	protected int nbImagesHeight;
	
	protected SegmentedImageRGB(int width, int height, RGBImage[][] images, int blockSize) {
		super(width, height);
		this.blockSize = blockSize;
		this.images = images;
		this.nbImagesWidth = (int) Math.ceil((double)width / (double)blockSize);
		this.nbImagesHeight = (int) Math.ceil((double)height / (double)blockSize);
	}
	
	public SegmentedImageRGB(int width, int height, int blockSize) {
		this(width, height, new RGBImage[(int) Math.ceil((double)width / (double)blockSize)][(int) Math.ceil((double)height / (double)blockSize)], blockSize);
	}
	
	public SegmentedImageRGB(RGBImage image, int blockSize) {
		this(image.getWidth(), image.getHeight(), blockSize);
		
		for(int i = 0; i < image.getWidth(); i += blockSize) {
			for(int j = 0; j < image.getHeight(); j += blockSize) {
				
				int widthSubImage = (image.getWidth() - i > blockSize) ? blockSize : image.getWidth() - i;
				int heightSubImage = (image.getHeight() - j > blockSize) ? blockSize : image.getHeight() - j;
				
				RGBImage subImage = image.getSubImage(i, j, widthSubImage, heightSubImage);
				
				this.setImage(i / blockSize, j / blockSize, subImage);
			}
		}
	}

	@Override
	public RGBImage getImage(int u, int v) {
		return this.images[u][v];
	}

	@Override
	public void setImage(int u, int v, RGBImage image) {
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
				data[i][j] = this.getRGB(i + iStart, j + jStart);
			}
		}
		return new RGBImage(data);
	}
	

	@Override
	public SegmentedImageRGB clone() {
		SegmentedImageRGB clone = (SegmentedImageRGB) super.clone();
		
		for(int u = 0; u < this.getNbImagesWidth(); u++) {
			for(int v = 0; v < this.getNbImagesHeight(); v++) {
				clone.setImage(u, v, this.getImage(u, v).clone());
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
