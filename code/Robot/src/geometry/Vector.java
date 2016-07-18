package geometry;


public class Vector {
	
	private int x;
	private int y;
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
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
	
	
	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}


	public static class Test {
		public static void main(String[] args) {
			
			//Point A = new Point(0, 0);
			//Point B = new Point(0, -8);
			
			Vector v1 = new Vector(0, -8);
			Vector v2 = new Vector(0, 0);
			
			System.out.println(v1.getMagnitude() + " " + v2.getMagnitude());
			System.out.println(Vector.scalar(v1, v2));
			System.out.println(Vector.getAngle(v1, v2));
		}
	}
	
}
