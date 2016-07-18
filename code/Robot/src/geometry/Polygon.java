package geometry;

import java.util.ArrayList;
import java.util.List;



public class Polygon {

	private List<Point> points;
	
	public Polygon() {
		this.points = new ArrayList<Point>();
	}
	
	public Polygon(List<Point> points) {
		this.points = points;
	}
	
	public void addPoint(Point point) {
		this.points.add(point);
	}
	
	public void addPoint(int x, int y) {
		this.addPoint(new Point(x, y));
	}
		

	public List<Point> getPoints() {
		return this.points;
	}
	
	public int getNbPoints() {
		return this.points.size();
	}
	
	
	@Override
	public String toString() {
		return "" + this.points;
	}
	
}
