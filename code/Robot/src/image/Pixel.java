package image;

import java.awt.Color;

/**
 * Represents a pixel by 3 values : 
 * - red 
 * - green 
 * - blue 
 * - (alpha) 
 * 
 * Mostly use the Color class. 
 * 
 * @author Maxime PINEAU
 * @see Image 
 */
public class Pixel {
	// http://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values/25761567
		
	private int red;
	private int green;
	private int blue;
	private int alpha;
	
	/**
	 * Creates a black pixel 
	 */
	public Pixel() {
		this(0, 0, 0);
	}
	
	/**
	 * Creates a pixel using the alpha red blue and green values given in a single integer 
	 * @param argb the alpha, red, green, blue values in a single integer 
	 */
	public Pixel(int argb) {
		this(new Color(argb));
	}
	
	/**
	 * Creates a pixel using a given color 
	 * @param color the color used to create the pixel 
	 */
	public Pixel(Color color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	/**
	 * Creates a pixel using the red, green and blue values. 
	 * The alpha value will be 0.
	 * @param red the red channel 
	 * @param green the green channel 
	 * @param blue the blue channel 
	 */
	public Pixel(int red, int green, int blue) {
		this(red, green, blue, 0);
	}
	
	/**
	 * Creates a pixel using the red, green , blue and alpha values 
	 * @param red the red channel 
	 * @param green the green channel 
	 * @param blue the blue channel 
	 * @param alpha the alpha (transparency) channel 
	 */
	public Pixel(int red, int green, int blue, int alpha) {
		this.red = Math.min(255, Math.max(0, red));
		this.green = Math.min(255, Math.max(0, green));
		this.blue = Math.min(255, Math.max(0, blue));
		this.alpha = Math.min(255, Math.max(0, alpha));
	}


	public int getGrey() {
		return (this.red + this.green + this.blue) / 3;
	}
	
	public int getARGB() {
		return (new Color(this.red, this.green, this.blue, this.alpha)).getRGB();
	}
	
	public int getRGB() {
		return (new Color(this.red, this.green, this.blue)).getRGB();
	}
	
	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getAlpha() {
		return alpha;
	}
	
	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	
	
	
	
	/**
	 * Returns the color given the alpha, red, green and blue values. 
	 * Thoses values are gived in a single integer 
	 * @param argb the alpha, red, green and blue values in a single integer 
	 * @return the corresponding color 
	 */
	public static Color getColor(int argb) {
		return new Color(argb);
	}
	
	/**
	 * Returns the red value of the argb (alpha, reg, green and blue in a single integer) 
	 * @param argb the alpha, red, green and blue values in a single integer 
	 * @return the red value 
	 */
	public static int getRed(int argb) {
		return Pixel.getColor(argb).getRed();
	}
	
	/**
	 * @param argb the alpha, red, green and blue values in a single integer 
	 * @return the green value 
	 */
	public static int getGreen(int argb) {
		return Pixel.getColor(argb).getGreen();
	}
	
	/**
	 * @param argb the alpha, red, green and blue values in a single integer 
	 * @return the blue value 
	 */
	public static int getBlue(int argb) {
		return Pixel.getColor(argb).getBlue();
	}
	
	/**
	 * @param argb the alpha, red, green and blue values in a single integer 
	 * @return the corresponding grey value 
	 */
	public static int getGrey(int argb) {
		Color color = Pixel.getColor(argb);
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
	
	/**
	 * Returns the red, green and blue values in a single integer. 
	 * @param red the red channel 
	 * @param green the green channel 
	 * @param blue the blue channel 
	 * @return the red, green and blue value in a single integer 
	 */
	public static int getRGB(int red, int green, int blue) {
		return (new Color(red, green, blue)).getRGB();
	}
	
	/** 
	 * Returns the alpha, red, green and blue values in a single integer. 
	 * @param red the red channel 
	 * @param green the green channel 
	 * @param blue the blue channel 
	 * @param alpha the alpha (transparency) channel 
	 * @return the alpha, red, green and blue value in a single integer 
	 */
	public static int getRGB(int red, int green, int blue, int alpha) {
		return (new Color(red, green, blue, alpha)).getRGB();
	}
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + red;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (blue != other.blue)
			return false;
		if (green != other.green)
			return false;
		if (red != other.red)
			return false;
		return true;
	}
	
	
}
