package image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class SegmentedImage extends Image {

	public static final int BLOCK_SIZE = 805;
	
	protected Color gridColor = Color.MAGENTA;
	
	protected Image[][] subImages;
	protected int blockSize;
	
	protected int nbImagesWidth;
	protected int nbImagesHeight;
	
	public SegmentedImage(int width, int height, Image[][] images, int blockSize) {
		super(width, height);
		
		this.blockSize = blockSize;
		this.nbImagesWidth = (int) Math.ceil((double)width / (double)blockSize);
		this.nbImagesHeight = (int) Math.ceil((double)height / (double)blockSize);
		this.subImages = images;
	}
	
	public SegmentedImage(int width, int height, int blockSize) {
		this(width, height, new Image[width][height], blockSize);
	}
	
	public SegmentedImage(Image image, int blockSize) {
		super(image.getWidth(), image.getHeight());
				
		// exception when blockSize > width || > height
		this.blockSize = blockSize;
		this.nbImagesWidth = (int) Math.ceil((double)image.getWidth() / (double)blockSize);
		this.nbImagesHeight = (int) Math.ceil((double)image.getHeight() / (double)blockSize);
		this.subImages = new Image[nbImagesWidth][nbImagesHeight];
		
		for(int i = 0; i < image.getWidth(); i += blockSize) {
			for(int j = 0; j < image.getHeight(); j += blockSize) {
				
				int widthSubImage = (image.getWidth() - i > blockSize) ? blockSize : image.getWidth() - i;
				int heightSubImage = (image.getHeight() - j > blockSize) ? blockSize : image.getHeight() - j;
				
				Image subImage = image.getSubImage(i, j, widthSubImage, heightSubImage);
				
				//subImagesLine.add(subImage);
				this.subImages[i / blockSize][j / blockSize] = subImage;
			}
		}
		
	}
	
	public Image getImage(int u, int v) {
		return this.subImages[u][v];
	}
	
	public void setImage(int u, int v, Image image) {
		this.subImages[u][v] = image;
	}
	

	@Override
	public Pixel getPixel(int i, int j) {
		Image image = this.getImage(i / this.blockSize, j / this.blockSize); 
		return image.getPixel(i % this.blockSize, j % this.blockSize);
	}

	@Override
	public void set(int i, int j, Pixel pixel) {
		Image image = this.getImage(i % this.blockSize, j % this.blockSize); 
		image.set(i % this.blockSize, j % this.blockSize, pixel);
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

		graphics.setPaint(this.gridColor);
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
	

	public int getBlockSize() {
		return blockSize;
	}

	public int getNbImagesWidth() {
		return nbImagesWidth;
	}

	public int getNbImagesHeight() {
		return nbImagesHeight;
	}

	public int getNbSubImages() {
		return this.nbImagesHeight * this.nbImagesWidth;
	}
	
	@Override
	public void accept(VisitorImage visitorImage) {
		visitorImage.visit(this);		
	}

	@Override
	public Image getSubImage(int iStart, int jStart, int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
