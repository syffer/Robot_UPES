package model.image;

import java.awt.Color;

public class Pixel {
	// http://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values/25761567
		
	private int red;
	private int green;
	private int blue;
	private int alpha;
	
	public Pixel() {
		this(0, 0, 0);
	}
	
	public Pixel(int argb) {
		this(new Color(argb));
	}
	
	public Pixel(Color color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public Pixel(int red, int green, int blue) {
		this(red, green, blue, 0);
	}
	
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
	
	
	
	
	public static Color getColor(int argb) {
		return new Color(argb);
	}
	
	public static int getRed(int argb) {
		return Pixel.getColor(argb).getRed();
	}
	
	public static int getGreen(int argb) {
		return Pixel.getColor(argb).getGreen();
	}
	
	public static int getBlue(int argb) {
		return Pixel.getColor(argb).getBlue();
	}
	
	public static int getGrey(int argb) {
		Color color = Pixel.getColor(argb);
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
	
	public static int getRGB(int red, int green, int blue) {
		return (new Color(red, green, blue)).getRGB();
	}
	
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
