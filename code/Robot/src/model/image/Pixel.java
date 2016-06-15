package model.image;

public class Pixel {
	// http://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values/25761567
		
	private int r;
	private int g;
	private int b;
	private int l;
	
	public Pixel(int grey) {
		this(grey, grey, grey);
	}
	
	public Pixel(int r, int g, int b) {
		this(r, g, b, 0);
	}
	
	public Pixel(int r, int g, int b, int l) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.l = l;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int getL() {
		return l;
	}
	
	public int getGrey() {
		return (this.r + this.g + this.b) / 3;
	}
	
	public int getRGBL() {
		return 0;
	}
	
	/*
	public static int getR(int rgb) {
		return (rgb >> 16) & 0x000000FF;
	}
	
	public static int getG(int rgb) {
		return 0;
	}
	*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
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
		if (b != other.b)
			return false;
		if (g != other.g)
			return false;
		if (r != other.r)
			return false;
		return true;
	}
	
	
}
