package features;

import java.util.List;

import geometry.Point;
import geometry.Polygon;
import geometry.Vector;

/**
 * Represents a feature in an image. 
 * The feature is created by giving a chain code representing the feature's border, and a starting point.
 * 
 * Multiples others information are determined from the chain code (e.g. width, heidht, perimeter, ...). 
 * 
 * @see features.FeatureExtractor 
 * @see features.ChainCode 
 * @author Maxime PINEAU
 */
public class Feature {
	
	// https://books.google.co.in/books?id=M_Lr8NTfAHcC&pg=PA226&lpg=PA226&dq=curvature+with+chain+code&source=bl&ots=hNHF5hTbuM&sig=S4scMfCxCVUUHFms09hFjCMt7RU&hl=fr&sa=X&redir_esc=y#v=onepage&q=curvature%20with%20chain%20code&f=false 
	// https://www.uio.no/studier/emner/matnat/ifi/INF4300/h15/undervisningsmateriale/inf4300-2015-f06-description.pdf (page 9) 
	// https://books.google.co.in/books?id=_SPyCAAAQBAJ&pg=PA507&lpg=PA507&dq=area+chain+code&source=bl&ots=N26V9BAEdW&sig=p3kAcg1ag0vbPX7v4IPl-_fz6Rc&hl=fr&sa=X&redir_esc=y#v=onepage&q=area%20chain%20code&f=false 
		
	private double area; 		// number of pixels in the region
	private double perimeter;
	
	private double compactness;
	private double circularity;
	private double curvature; 		// represent the successive changes in direction (in radian)
	private double bendingEnergy;
	
	private int width;
	private int height;
	private double ratioWidthHeight;
	
	
	public Feature(double area, double perimeter, double compactness, double circularity, double curvature, double bendingEnergy, int width, int height, double ratioWidthHeight) {
		this.area = area;
		this.perimeter = perimeter; 
		this.compactness = compactness;
		this.circularity = circularity;
		this.curvature = curvature;
		this.bendingEnergy = bendingEnergy;
		this.width = width;
		this.height = height;
		this.ratioWidthHeight = ratioWidthHeight;
	}
	
	public Feature(ChainCode chainCode) {
		ChainCode differentialChainCode = chainCode.getDifferenceChainCode();
		
		this.perimeter = 0;
		this.area = 0;
		this.curvature = 0;
		this.bendingEnergy = 0;
		
		int b = 1;
		
		//for(int code : chainCode) {
		for(int i = 0; i < chainCode.size(); i++) {
			int code = chainCode.get(i);
			
			// perimeter 
			if(code == 0 || code == 2 || code == 4 || code == 6) this.perimeter += 1;  // 
			else this.perimeter += Math.sqrt(2); 	// diagonal move 
			
			// area 
			if(code == 0) {
				this.area += b;
			} else if(code == 1) {
				this.area += b;
				b++;
			} else if(code == 2) {
				b++;
			} else if(code == 3) {
				this.area -= b;
				b++;
			} else if(code == 4) {
				this.area -= b;
			} else if(code == 5) {
				this.area -= b;
				b--;
			} else if(code == 6) {
				b--;
			} else {
				this.area += b;
				b--;
			}
			
			// height + width , always start from the upper left pixel 
			if(code == 5 || code == 6 || code == 7) this.height++; 
			if(code == 0 || code == 1 || code == 7) this.width++;
						
			// curvature + bending energy
			int diffCode = differentialChainCode.get(i); 	// in "degree / 45" 
			int angleDegree = diffCode * 45;
			double angleRadian = Math.toRadians(angleDegree);
			
			this.curvature += angleRadian;
			this.bendingEnergy += angleRadian * angleRadian;
			
		}
		
		this.perimeter = this.perimeter * (1 + Math.sqrt(2)) * (Math.PI / 8); 	// https://www.uio.no/studier/emner/matnat/ifi/INF4300/h15/undervisningsmateriale/inf4300-2015-f06-description.pdf (page 10, 11) 
		this.compactness = (this.area > 0) ? this.perimeter * this.perimeter / this.area : 0; 
		this.circularity = 4 * Math.PI * this.area / (this.perimeter * this.perimeter);
		this.ratioWidthHeight = (double)this.width / (double)this.height;
		this.bendingEnergy /= differentialChainCode.size() - 1; 
	}


	public Feature(Polygon polygon) {
		this.perimeter = 0;
		this.area = 0;
				
		List<Point> points = polygon.getPoints();
		Point previousPoint = points.get(points.size() - 1);
		
		int minimalX = previousPoint.x;
		int maximalX = previousPoint.x;
		
		int minimalY = previousPoint.y;
		int maximalY = previousPoint.y;
		
		for(int i = 0; i< points.size(); i++) {
			Point actualPoint = points.get(i);
			Point nextPoint = points.get((i+1) % points.size());
			
			// area and perimeter
			this.area += (previousPoint.x * actualPoint.y) - (actualPoint.x * previousPoint.y);
			
			double diffX = (double)actualPoint.x - (double)previousPoint.x;
			double diffY = (double)actualPoint.y - (double)previousPoint.y;
			this.perimeter += Math.sqrt(diffX*diffX + diffY*diffY);
			
			// width and height 
			minimalX = Math.min(minimalX, actualPoint.x);
			maximalX = Math.max(maximalX, actualPoint.x);
			minimalY = Math.min(minimalY, actualPoint.y);
			maximalY = Math.max(maximalY, actualPoint.y);
			
			// curvature and bending energy 
			Vector di = new Vector(previousPoint, actualPoint);
			Vector dii = new Vector(actualPoint, nextPoint);
			
			double angleRadian = Vector.getAngle(di, dii);
			this.curvature += angleRadian;
			this.bendingEnergy += angleRadian * angleRadian;
					
			previousPoint = actualPoint;
		}
		
		this.area = Math.abs(this.area) / 2;
		this.compactness = (this.area > 0) ? this.perimeter * this.perimeter / this.area : 0; 
		this.circularity = 4 * Math.PI * this.area / (this.perimeter * this.perimeter); 
		
		this.width = maximalX - minimalX;
		this.height = maximalY - minimalY;
		this.ratioWidthHeight = (double)this.width / (double)this.height;
		
		this.bendingEnergy /= points.size();
		
		//System.out.println(this.area + " " + this.perimeter + " " + this.compactness + " " + this.circularity + " " + this.curvature + " " + this.bendingEnergy + " " + this.width + " " + this.height);
	}
		
	


	public double getArea() {
		return area;
	}


	public double getPerimeter() {
		return perimeter;
	}


	public double getCompactness() {
		return compactness;
	}


	public double getCircularity() {
		return circularity;
	}


	public double getCurvature() {
		return curvature;
	}


	public double getBendingEnergy() {
		return bendingEnergy;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public double getRatioWidthHeight() {
		return ratioWidthHeight;
	}

	
	@Override
	public String toString() {
		return "Feature [ " 
				+ "\n\t area=" + area + ", perimeter=" + perimeter
				+ "\n\t compactness=" + compactness + ", circularity=" + circularity 
				+ "\n\t curvature=" + curvature + ", bendingenergy=" + bendingEnergy 
				+ "\n\t width=" + width + ", height=" + height
				+ "\n\t ratioWidthHeight=" + ratioWidthHeight
				+ " ]";
	}
	
	

	
	public static class Test {
		public static void main(String[] args) {
			Polygon polygon = new Polygon();
			//polygon.addPoint(-3, -2);
			//polygon.addPoint(-1, 4);
			//polygon.addPoint(6, 1);
			//polygon.addPoint(3, 10);
			//polygon.addPoint(-4, 9);
			
			polygon.addPoint(0, 0);
			polygon.addPoint(3, 0);
			polygon.addPoint(3, 2);
			polygon.addPoint(0, 2);
			
			
			Feature feature = new Feature(polygon);
			System.out.println(feature.getArea());
			System.out.println(feature.getWidth() + " " + feature.getHeight());
		}
	}
		
}
