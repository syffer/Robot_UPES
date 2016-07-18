package geometry;


public class Vector {
	
	private int x;
	private int y;
	
	public Vector(Point A, Point B) {
		this.x = B.x - A.x;
		this.y = B.y - A.y;
	}
	
	
	public double getMagnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public static double scalar(Vector u, Vector v) {
		return u.x * v.x + u.y * v.y; 
	}
	
	public static double getAngle(Vector u, Vector v) { 
		double cosTheta = Vector.scalar(u, v) / (u.getMagnitude() * v.getMagnitude());
		return Math.acos(cosTheta);
	}
	
}
