package transform.fourier;

public class Complex {
	
	private double real;
	private double image;
	
	public Complex(double real, double image) {
		this.real = real;
		this.image = image;
	}
	
	public double getMagnetude() {
		return Math.sqrt(this.real * this.real + this.image * this.image);
	}
	
	
	public double getPhase() {
		return Math.atan(this.image / this.real);
	}
	
	
	
	public static Complex add(Complex a, Complex b) {
		return new Complex(a.real + b.real, a.image + b.image);
	}
	
	public static Complex sub(Complex a, Complex b) {
		return new Complex(a.real - b.real, a.image - b.image);
	}
	
	public static Complex mul(Complex a, Complex b) {
		return new Complex(a.real * b.real - a.image * b.image, a.real * b.image + a.image * b.real);
	}
	
	
}
